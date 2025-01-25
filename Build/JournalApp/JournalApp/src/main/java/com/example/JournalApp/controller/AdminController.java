package com.example.JournalApp.controller;

import com.example.JournalApp.entity.User;
import com.example.JournalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers()
    {
        List<User> all=userService.getall();
        if(all!=null &&  !all.isEmpty())
        {
           return new ResponseEntity<>(all,HttpStatus.OK);
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createAdminUser(@RequestBody User user)
    {
        userService.saveAdmin(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
