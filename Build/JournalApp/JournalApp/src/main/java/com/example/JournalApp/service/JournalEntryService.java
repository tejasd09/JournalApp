package com.example.JournalApp.service;

import com.example.JournalApp.entity.JournalEntry;
import com.example.JournalApp.entity.User;
import com.example.JournalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    public void saveNewUser(JournalEntry journalEntry, String userName)
    {
        try {
            User user=userService.findbyUserName(userName);

            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved=journalEntryRepository.save(journalEntry);

            if (user.getJournalEntries() == null) {
                System.out.println("journalEntries is null, initializing...");
                user.setJournalEntries(new ArrayList<>());
            } else {
                System.out.println("journalEntries size: " + user.getJournalEntries().size());
            }

            user.getJournalEntries().add(saved);
//            user.setUserName(null);
            userService.saveUser(user);
        }catch(Exception e)
        {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry:" + e);
        }

    }

    public void saveUser(JournalEntry journalEntry)
    {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getall()
    {
       return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> find(@PathVariable  ObjectId id)
    {
       return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName)
    {
        try{
            User user=userService.findbyUserName(userName);
            boolean removed= user.getJournalEntries().removeIf(x-> x.getId().equals(id));
            if(removed)
            {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }
        }catch(Exception e)
        {
            System.out.println(e);
            throw  new RuntimeException("An error occured during deletion of an entry");
        }


    }

    public User findByUserName(String userName){
        return userService.findbyUserName(userName);
    }
}
