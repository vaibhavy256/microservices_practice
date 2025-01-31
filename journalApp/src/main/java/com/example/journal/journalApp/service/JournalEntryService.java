package com.example.journal.journalApp.service;

import com.example.journal.journalApp.entity.JournalEntry;
import com.example.journal.journalApp.entity.User;
import com.example.journal.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserEntryService userEntryService;

//    public JournalEntryService(UserEntryService userEntryService) {
//        this.userEntryService = userEntryService;
//    }


    public void saveEntry(JournalEntry journalEntry, String username){
        try {
            User user = userEntryService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userEntryService.saveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }
    }

    public void saveEntry(JournalEntry old) {
        journalEntryRepository.save(old);
    }

    public List<JournalEntry>getAllJournalEntries(){
        return journalEntryRepository.findAll();
    }

    public JournalEntry getJournalById(ObjectId id){
        return journalEntryRepository.findById(id).orElse(null);
    }

    public void deleteEntry(ObjectId id,String username){
        User user=userEntryService.findByUserName(username);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userEntryService.saveNewUser(user);
        journalEntryRepository.deleteById(id);
    }


}
