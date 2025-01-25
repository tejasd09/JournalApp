package com.example.JournalApp.service;

import com.example.JournalApp.entity.User;
import com.example.JournalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @InjectMocks
    private  UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsernameTest()
    {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("tejas").password("asdbdnfskdvbsj").roles(new ArrayList<>()).build());
        UserDetails user =userDetailsService.loadUserByUsername("tejas");
        Assertions.assertNotNull(user);
    }

}
