package com.api.gym.controllers;

import com.api.gym.exceptions.RoleNotFoundException;
import com.api.gym.models.ERole;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangeRole;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.repository.RoleRepository;
import com.api.gym.repository.UserRepository;
import com.api.gym.service.AllUsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminController
{
    AllUsersService allUsersService;

    AdminController(AllUsersService allUsersService)
    {
        this.allUsersService = allUsersService;
    }

    @ApiOperation(value = "Show main site for admin")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>>adminMainSite()
    {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("newUsersToday", "1000");
        response.put("soldTicketsToday", "100");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/trainer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>showTrainers()
    {
        return new ResponseEntity<>(allUsersService.findUsersByRole(ERole.ROLE_TRAINER), HttpStatus.OK);
    }

    @GetMapping("/basic")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTrainer(@Valid @RequestBody ChangeRole email)
    {
        return new ResponseEntity<>(allUsersService.changeRoleByEmail(email.getEmail(), ERole.ROLE_TRAINER, allUsersService.userDetails()), HttpStatus.OK);
    }

}
