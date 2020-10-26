package com.api.gym.controllers.basic;

import com.api.gym.models.User;
import com.api.gym.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/basicuser")
public class BasicUserController
{
    UserRepository userRepository;
    public BasicUserController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


}