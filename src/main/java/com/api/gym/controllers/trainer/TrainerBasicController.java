package com.api.gym.controllers.trainer;

import com.api.gym.enums.ERole;
import com.api.gym.models.User;
import com.api.gym.payload.request.EmailRequest;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.converters.Converter;
import com.api.gym.service.repository.RoleService;
import com.api.gym.service.repository.UserService;
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
    private final UserService userService;
    private final RoleService roleService;
    private final Converter converter;

    TrainerBasicController(UsersService usersService, UserService userService, RoleService roleService, Converter converter)
    {
        this.usersService = usersService;
        this.userService = userService;
        this.roleService = roleService;
        this.converter = converter;
    }

    @GetMapping("/basic")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showBasicUsers(@RequestParam(value = "email", required = false) String email)
    {
        if(email == null)
        {
            List<ShowUserResponse> showBasicUsers = new ArrayList<>(converter.convertUserListToShowUserResponseCollection(userService.findUsersByRole(roleService.findRoleByName(ERole.ROLE_BASIC))));
            return ResponseEntity.ok(showBasicUsers);
        }
        else
        {
            User user = userService.findUserByEmail(email);
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
        return ResponseEntity.ok(userService.deleteByEmail(emailRequest.getEmail()));
    }
}
