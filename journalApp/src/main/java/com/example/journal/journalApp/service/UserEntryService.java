package com.example.journal.journalApp.service;

import com.example.journal.journalApp.entity.JournalEntry;
import com.example.journal.journalApp.entity.User;
import com.example.journal.journalApp.repository.JournalEntryRepository;
import com.example.journal.journalApp.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserEntryService {
    @Autowired
    private UserEntryRepository userEntryRepository;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

    public User saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("Normal"));
        User userEntry= userEntryRepository.save(user);
        return userEntry;
    }

    public void saveUser(User user){
        userEntryRepository.save(user);
    }
    public List<User> getAllUserEntries(){
        return userEntryRepository.findAll();
    }

    public User getUserById(ObjectId id){
        return userEntryRepository.findById(id).orElse(null);
    }

    public User findByUserName(String userName){
        System.out.println("Searching for user: " + userName);
        User user = userEntryRepository.findByUserName(userName);
        if (user == null) {
            System.out.println("User not found in database: " + userName);
        } else {
            System.out.println("User found: " + user);
        }
        return user;
    }

}
