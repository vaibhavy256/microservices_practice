package com.example.journal.journalApp.controller;

import com.example.journal.journalApp.entity.User;
import com.example.journal.journalApp.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserEntryService userEntryService;

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        try{
            userEntryService.saveNewUser(user);
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
}
