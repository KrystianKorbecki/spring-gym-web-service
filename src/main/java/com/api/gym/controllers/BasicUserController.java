package com.api.gym.controllers;

import com.api.gym.models.User;
import com.api.gym.repository.UserRepository;
import com.api.gym.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/basic")
public class BasicUserController
{
    private final UsersService usersService;
    private final UserRepository userRepository;
    public BasicUserController(UsersService usersService, UserRepository userRepository)
    {
        this.usersService = usersService;
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("hasRole('BASIC')")
    public ResponseEntity<?> showMainSite()
    {
        User user = userRepository.findUserByEmail(usersService.userDetails().getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("code",user.getCode());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
