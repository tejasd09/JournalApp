package com.example.JournalApp.controller;

import com.example.JournalApp.apiResponse.WeatherResponse;
import com.example.JournalApp.entity.User;
import com.example.JournalApp.repository.UserRepository;
import com.example.JournalApp.service.UserService;
import com.example.JournalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user)
    {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User userInDB=userService.findbyUserName(userName);
        userInDB.setUserName(user.getUserName());
        userInDB.setPassword(user.getPassword());
        userService.saveNewEntry(userInDB);//in saveall() password is encoded
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping
    public ResponseEntity<?> deletebyId()
    {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping
    public ResponseEntity<?> get()
    {
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse =weatherService.getWeather("Pune");
        String greeting="";
        if(weatherResponse!=null && weatherResponse.getCurrent()!=null)
        {
            greeting=" Weather feels like: " + weatherResponse.getCurrent().getFeelslike() + weatherResponse.getCurrent().getWeatherDescriptions();
        }
        else {
            greeting =" Weather currently not available";
        }
        return new ResponseEntity<>("Hii "+ authentication.getName() + greeting , HttpStatus.OK);
    }






}
