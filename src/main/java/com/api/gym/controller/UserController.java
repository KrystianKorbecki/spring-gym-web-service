package com.api.gym.controller;

import com.api.gym.model.AuthRequest;
import com.api.gym.model.UserRequest;
import com.api.gym.service.RegistrationService;
import com.api.gym.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController
{
    private RegistrationService registrationService;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    UserController(RegistrationService registrationService, JwtUtil jwtUtil, AuthenticationManager authenticationManager)
    {
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody UserRequest newUser)
    {
        return registrationService.createUser(newUser);
    }

    @PostMapping("/login")
    public String generateToken(@RequestBody AuthRequest authRequest) throws Exception
    {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmailAddress(), authRequest.getPassword()));
            return jwtUtil.generateToken(authRequest.getEmailAddress());
        }catch (Exception e)
        {
            throw new Exception("invalid username or password");
        }

    }
}
