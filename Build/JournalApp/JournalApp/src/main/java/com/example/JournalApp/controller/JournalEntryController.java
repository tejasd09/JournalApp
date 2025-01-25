package com.example.JournalApp.controller;

import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import com.example.JournalApp.service.JournalEntryService;
import com.example.JournalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<JournalEntry> create(@RequestBody JournalEntry entry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        try {
            journalEntryService.saveNewUser(entry,userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping()
    public ResponseEntity<?> getallJournalEntriesofUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user=userService.findbyUserName(userName);
        List<JournalEntry> all=user.getJournalEntries();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{myId}")

    public ResponseEntity<?> getJournalEntryById(@PathVariable  ObjectId myId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user=userService.findbyUserName(userName);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> journalEntry=journalEntryService.find(myId);
            if(journalEntry.isPresent())
            {
                return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @DeleteMapping("/id/{myId}")
    public boolean delete(@PathVariable ObjectId myId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        journalEntryService.deleteById(myId,userName);
        return true;
    }

    @PutMapping("id/{myId}")
    public ResponseEntity<?> update(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();

        User user=userService.findbyUserName(userName);
        List<JournalEntry> collect=user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());

        if(!collect.isEmpty())
        {
            Optional<JournalEntry> journalEntry=journalEntryService.find(myId);
            if(journalEntry.isPresent())
            {
                JournalEntry old=journalEntry.get();
                old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveUser(old);
                return new ResponseEntity<>(old,HttpStatus.OK);
            }
        }

       JournalEntry old= journalEntryService.find(myId).orElse(null);

       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
}
