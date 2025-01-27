package com.example.journal.journalApp.service;

import com.example.journal.journalApp.entity.JournalEntry;
import com.example.journal.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public JournalEntry saveEntry(JournalEntry journalEntry){
       JournalEntry myEntry= journalEntryRepository.save(journalEntry);
        return myEntry;
    }
    public List<JournalEntry>getAllJournalEntries(){
        return journalEntryRepository.findAll();
    }

    public JournalEntry getJournalById(ObjectId id){
        return journalEntryRepository.findById(id).orElse(null);
    }

    public JournalEntry updateEntry(ObjectId id,JournalEntry entry){
        return null;
    }
}
