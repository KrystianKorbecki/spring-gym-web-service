package com.api.gym.controllers;

import com.api.gym.models.ERole;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangeRole;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.repository.UserRepository;
import com.api.gym.security.services.UserDetailsImpl;
import com.api.gym.service.AllUsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/trainer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrainerController
{
    private final AllUsersService allUsersService;

    TrainerController(AllUsersService allUsersService)
    {
        this.allUsersService = allUsersService;
    }

    @ApiOperation(value = "Show main site for trainer")
    @GetMapping
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<Map<String,String>>trainerMainSite()
    {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("newUsersToday", "1000");
        response.put("soldTicketsToday", "100");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?>showUsers()
    {
        return new ResponseEntity<>(allUsersService.findUsersByRole(ERole.ROLE_USER), HttpStatus.OK);
    }

    @GetMapping("/basic")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?>showBasicUsers(@RequestParam(value = "email", required = false) String email)
    {
        List<ShowUserResponse> showAllTrainersResponses = new ArrayList<>();
        if(email == null)
        {
            showAllTrainersResponses.addAll(allUsersService.findUsersByRole(ERole.ROLE_BASIC));
        }
        else
        {
            User user = allUsersService.findUserByEmail(email);
            showAllTrainersResponses.add(new ShowUserResponse(user.getUserName(), user.getLastName(), user.getEmail(), user.getPhoneNumber()));
        }

        return new ResponseEntity<>(showAllTrainersResponses, HttpStatus.OK);
    }

    @PatchMapping("/basic")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> addUser(@Valid @RequestBody ChangeRole email)
    {
        return allUsersService.changeRoleByEmail(email.getEmail(), ERole.ROLE_USER, allUsersService.userDetails());
    }

}
