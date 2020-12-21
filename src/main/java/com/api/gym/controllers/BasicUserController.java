package com.api.gym.controllers;

import com.api.gym.models.User;
import com.api.gym.repository.UserRepository;
import com.api.gym.service.repository.UserService;
import com.api.gym.service.users.UsersService;
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
    private final UserService userService;
    public BasicUserController(UsersService usersService, UserService userService)
    {
        this.usersService = usersService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('BASIC')")
    public ResponseEntity<?> showMainSite()
    {
        User user = userService.findUserByEmail(usersService.userDetails().getEmail());
        Map<String, String> response = new HashMap<>();
        response.put("code",user.getCode());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
