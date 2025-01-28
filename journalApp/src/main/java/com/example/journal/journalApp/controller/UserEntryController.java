package com.example.journal.journalApp.controller;

import com.example.journal.journalApp.entity.JournalEntry;
import com.example.journal.journalApp.entity.User;
import com.example.journal.journalApp.service.JournalEntryService;
import com.example.journal.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    UserEntryService userEntryService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        try{
            userEntryService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.OK) ;
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?>getAll(){
        List<User> allEntries=userEntryService.getAllUserEntries();
        if(allEntries!=null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries,HttpStatus.FOUND);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<User> getById(@PathVariable ObjectId id){
        User entries=userEntryService.getUserById(id);
        return new ResponseEntity<User>(entries,HttpStatus.FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id,@RequestBody User user){
        User old=userEntryService.findByUserName(user.getUserName());
        if(old!=null){
            old.setUserName(user.getUserName()!=null && !user.getUserName().equals("")?user.getUserName():old.getUserName());
            old.setPassword(user.getPassword()!=null && !user.getPassword().equals("")?user.getPassword(): old.getPassword());
            userEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
