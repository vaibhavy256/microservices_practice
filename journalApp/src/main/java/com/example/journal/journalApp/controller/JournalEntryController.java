package com.example.journal.journalApp.controller;

import com.example.journal.journalApp.entity.JournalEntry;
import com.example.journal.journalApp.repository.JournalEntryRepository;
import com.example.journal.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.sql.rowset.Joinable;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    JournalEntryService journalEntryService;

    @PostMapping("/add")
    public ResponseEntity<JournalEntry> addJournal(@RequestBody JournalEntry journalEntry){
        try{
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK) ;
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<?>getAll(){
        List<JournalEntry>allEntries=journalEntryService.getAllJournalEntries();
        if(allEntries!=null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries,HttpStatus.FOUND);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId id){
        JournalEntry entries=journalEntryService.getJournalById(id);
        return new ResponseEntity<JournalEntry>(entries,HttpStatus.FOUND);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEntry(@PathVariable ObjectId id,@RequestBody JournalEntry newEntry){
        JournalEntry old=journalEntryService.getJournalById(id);
        if(old!=null){
            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle():old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
