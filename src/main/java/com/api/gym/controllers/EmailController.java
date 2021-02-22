package com.api.gym.controllers;

import com.api.gym.mail.EmailService;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangePassword;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.repository.implementation.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class EmailController
{
    private final UserService userService;
    private final EmailService emailService;
    EmailController(UserService userService, EmailService emailService)
    {
        this.userService = userService;
        this.emailService = emailService;
    }

    @GetMapping("/confirm/{code}")
    public ResponseEntity<?> confirmEmail(@PathVariable String code)
    {
        User user = userService.findByConfirmationCode(code);
        user.setConfirmEmail(true);
        userService.saveUser(user);
        return new ResponseEntity<>(new MessageResponse("Email confirmed"), HttpStatus.ACCEPTED);
    }

    @PostMapping("/remind")
    public ResponseEntity<?> passwordReminder(@Valid @RequestBody String email)
    {
        User user = userService.findUserByEmail(email);
        emailService.changePasswordEmail(email, user);
        return new ResponseEntity<>(new MessageResponse("Email send"), HttpStatus.ACCEPTED);
    }

    @GetMapping("/remind/change/{code}")
    public ResponseEntity<?> changePassword(@PathVariable String code)
    {
        User user = userService.findByConfirmationCode(code);
        return new ResponseEntity<>(new MessageResponse(user.getUserName() + " " + user.getLastName()), HttpStatus.ACCEPTED);
    }

    @PostMapping("/remind/change/{code}")
    public ResponseEntity<?> changePassword(@PathVariable String code, @Valid @RequestBody ChangePassword changePassword)
    {
        User user = userService.findByConfirmationCode(code);
        if(user.getEmail().equals(changePassword.getEmail()))
        {
            user.setPassword(changePassword.getNewPassword());
            userService.saveUser(user);
            return new ResponseEntity<>(new MessageResponse("Password changed"), HttpStatus.CREATED);
        }
        else
        {
            return new ResponseEntity<>(new MessageResponse("Bad email addres"), HttpStatus.BAD_REQUEST);
        }
    }
}
