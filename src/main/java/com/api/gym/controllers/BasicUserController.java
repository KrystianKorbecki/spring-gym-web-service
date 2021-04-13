package com.api.gym.controllers;

import com.api.gym.models.User;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.repository.implementation.UserService;
import com.api.gym.service.users.UsersService;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    /**
     * This method shows only inform about role
     * @return {@link MessageResponse} with inform about role
     */
    @GetMapping
    @PreAuthorize("hasRole('BASIC')")
    public ResponseEntity<MessageResponse> showMainSite()
    {
        Link link = linkTo(methodOn(BasicUserController.class).showMainSite()).withSelfRel();
        return new ResponseEntity<>(new MessageResponse("You are not assigned any role").add(link), HttpStatus.OK);
    }

}
