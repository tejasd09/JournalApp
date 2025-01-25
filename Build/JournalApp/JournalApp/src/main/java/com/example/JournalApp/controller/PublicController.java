package com.example.JournalApp.controller;

import com.example.JournalApp.entity.User;
import com.example.JournalApp.service.UserDetailsServiceImpl;
import com.example.JournalApp.service.UserService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public boolean setUser(@RequestBody User user)
    {
            userService.saveNewEntry(user);
            return true;
    }


    @GetMapping("/criteria")
    public ResponseEntity<?> getByQuery()
    {
        return new ResponseEntity<>(userService.getByCriteria(),HttpStatus.OK);
    }
}
