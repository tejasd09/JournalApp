package com.example.JournalApp.service;

import com.example.JournalApp.entity.User;
import com.example.JournalApp.repository.UserRepository;
import com.example.JournalApp.repository.UserRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();



    public void saveNewEntry(User user)
    {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
        }catch(Exception e)
        {
            log.error("Error occured:");
        }

    }

    public void saveAdmin(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        userRepository.save(user);
    }

    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    public List<User> getall()
    {
       return userRepository.findAll();
    }

    public Optional<User> find(@PathVariable  ObjectId id)
    {
       return userRepository.findById(id);
    }

    public void deleteById(ObjectId id)
    {
        userRepository.deleteById(id);
    }

    public User findbyUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }

    public List<User> getByCriteria()
    {
        return userRepositoryImpl.getUserForSA();
    }
}
