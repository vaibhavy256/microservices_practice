package com.example.journal.journalApp.controller;

import com.example.journal.journalApp.entity.JournalEntry;
import com.example.journal.journalApp.repository.JournalEntryRepository;
import com.example.journal.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.rowset.Joinable;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    JournalEntryService journalEntryService;

    @PostMapping("/add")
    public JournalEntry addJournal(@RequestBody JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntry);
        return journalEntry;
    }

    @GetMapping("/all")
    public List<JournalEntry>getAll(){
        return journalEntryService.getAllJournalEntries();
    }

    @GetMapping("/get/{id}")
    public JournalEntry getById(@PathVariable ObjectId id){
        return journalEntryService.getJournalById(id);
    }

    @PutMapping("/update/{id}")
    public JournalEntry updateEntry(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry){
        JournalEntry old=journalEntryService.getJournalById(id);
        if(old!=null){
            old.setTitle(newEntry.getTitle()!=null &&newEntry.getTitle().equals("")?newEntry.getTitle():old.getTitle());
            old.setContent(newEntry.getContent()!=null && newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
        }
        journalEntryService.saveEntry(old);
        return  old;
    }


}
