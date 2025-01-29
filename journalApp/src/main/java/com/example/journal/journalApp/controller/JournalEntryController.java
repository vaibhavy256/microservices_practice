package com.example.journal.journalApp.controller;

import com.example.journal.journalApp.entity.JournalEntry;
import com.example.journal.journalApp.entity.User;
import com.example.journal.journalApp.repository.JournalEntryRepository;
import com.example.journal.journalApp.service.JournalEntryService;
import com.example.journal.journalApp.service.UserEntryService;
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

    @Autowired
    UserEntryService userEntryService;

    @PostMapping("/add/{username}")
    public ResponseEntity<JournalEntry> addJournal(@RequestBody JournalEntry journalEntry,@PathVariable String username){
        try{
            journalEntryService.saveEntry(journalEntry,username);
            return new ResponseEntity<>(journalEntry, HttpStatus.OK) ;
        }
        catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("{userName}")
    public ResponseEntity<?>getAllJournalEntriesOfUser(@PathVariable String userName){
        User user=userEntryService.findByUserName(userName);
        List<JournalEntry>allEntries=user.getJournalEntries();
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

    @PutMapping("/update/{id}/{username}")
    public ResponseEntity<?> updateEntry(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry newEntry,
            @PathVariable String username
    ){
        JournalEntry old=journalEntryService.getJournalById(id);
        if(old!=null){
            old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("")?newEntry.getTitle():old.getTitle());
            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("")? newEntry.getContent() : old.getContent());
            journalEntryService.saveEntry(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<?>deleteJournalById(@PathVariable ObjectId id,String username){
        journalEntryService.deleteEntry(id,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
