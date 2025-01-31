package com.example.journal.journalApp.controller;

import com.example.journal.journalApp.entity.JournalEntry;
import com.example.journal.journalApp.entity.User;
import com.example.journal.journalApp.service.JournalEntryService;
import com.example.journal.journalApp.service.UserEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    UserEntryService userEntryService;



    @GetMapping("/get/{id}")
    public ResponseEntity<User> getById(@PathVariable ObjectId id){
        User entries=userEntryService.getUserById(id);
        return new ResponseEntity<User>(entries,HttpStatus.FOUND);
    }

    @PutMapping("/update/")
    public ResponseEntity<?> updateUserEntry(@RequestBody User user){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User old=userEntryService.findByUserName(userName);
        if(old!=null){
            old.setUserName(user.getUserName()!=null && !user.getUserName().equals("")?user.getUserName():old.getUserName());
            old.setPassword(user.getPassword()!=null && !user.getPassword().equals("")?user.getPassword(): old.getPassword());
            userEntryService.saveNewUser(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
