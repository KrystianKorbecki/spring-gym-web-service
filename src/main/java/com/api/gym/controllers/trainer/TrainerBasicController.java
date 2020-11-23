package com.api.gym.controllers.trainer;

import com.api.gym.enums.ERole;
import com.api.gym.models.User;
import com.api.gym.payload.request.EmailRequest;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.service.repository.UserRepositoryService;
import com.api.gym.service.users.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trainer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrainerBasicController
{
    private final UsersService usersService;
    private final UserRepositoryService userRepositoryService;

    TrainerBasicController(UsersService usersService, UserRepositoryService userRepositoryService)
    {
        this.usersService = usersService;
        this.userRepositoryService = userRepositoryService;
    }

    @GetMapping("/basic")
    @PreAuthorize("hasRole('TRAINER')")
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
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> addUser(@Valid @RequestBody EmailRequest email)
    {
        return new ResponseEntity<>(usersService.changeRoleByEmail(email.getEmail(), ERole.ROLE_USER, usersService.userDetails(), ERole.ROLE_TRAINER), HttpStatus.OK);
    }


    @DeleteMapping("/basic")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?>deleteBasic(@Valid @RequestBody EmailRequest emailRequest)
    {
        return ResponseEntity.ok(userRepositoryService.deleteByEmail(emailRequest.getEmail()));
    }
}
