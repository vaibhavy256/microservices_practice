package com.example.journal.journalApp.service;

import com.example.journal.journalApp.entity.JournalEntry;
import com.example.journal.journalApp.entity.User;
import com.example.journal.journalApp.repository.JournalEntryRepository;
import com.example.journal.journalApp.repository.UserEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserEntryService {
    @Autowired
    private UserEntryRepository userEntryRepository;

    public User saveEntry(User user){
        User userEntry= userEntryRepository.save(user);
        return userEntry;
    }
    public List<User> getAllUserEntries(){
        return userEntryRepository.findAll();
    }

    public User getUserById(ObjectId id){
        return userEntryRepository.findById(id).orElse(null);
    }

    public User findByUserName(String userName){
        return userEntryRepository.findByUserName(userName);
    }

}
