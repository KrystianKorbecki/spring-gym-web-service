package com.api.gym.controllers.admin;

import com.api.gym.models.ERole;
import com.api.gym.models.User;
import com.api.gym.payload.request.EmailRequest;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.service.UserRepositoryService;
import com.api.gym.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminBasicsController
{
    private final UsersService usersService;
    private final UserRepositoryService userRepositoryService;


    AdminBasicsController(UsersService usersService, UserRepositoryService userRepositoryService)
    {
        this.usersService = usersService;
        this.userRepositoryService = userRepositoryService;
    }

    @GetMapping("/basic")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showBasicUsers(@RequestParam(value = "email", required = false) String email)
    {
        if(email == null)
        {
            List<ShowUserResponse> showBasicUsers = new ArrayList<>(usersService.findUsersByRole(ERole.ROLE_BASIC));
            return ResponseEntity.ok(showBasicUsers);
        }
        else
        {
            User user = userRepositoryService.findUserByEmail(email);
            return ResponseEntity.ok(user);
        }
    }

    @PatchMapping("/basic")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addTrainer(@Valid @RequestBody EmailRequest email)
    {
        return new ResponseEntity<>(usersService.changeRoleByEmail(email.getEmail(), ERole.ROLE_TRAINER, usersService.userDetails(), ERole.ROLE_ADMIN), HttpStatus.OK);
    }
}
