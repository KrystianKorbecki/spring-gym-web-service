package com.api.gym.service;


import com.api.gym.entity.User;
import com.api.gym.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService
{

    private UserRepository userRepository;

    @Autowired
    CustomUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException
    {
        User user = userRepository.findByEmailAddress(emailAddress);
        return new org.springframework.security.core.userdetails.User(user.getEmailAddress(),user.getPassword(), new ArrayList<>());
    }
}
