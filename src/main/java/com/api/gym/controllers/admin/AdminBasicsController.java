package com.api.gym.controllers.admin;

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
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminBasicsController
{
    private final UsersService usersService;
    private final UserService userService;
    private final Converter converter;
    private final RoleService roleService;


    AdminBasicsController(UsersService usersService, UserService userService, Converter converter, RoleService roleService)
    {
        this.usersService = usersService;
        this.userService = userService;
        this.converter = converter;
        this.roleService = roleService;
    }

    @GetMapping("/basic")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showBasicUsers(@RequestParam(value = "email", required = false) String email)
    {
        if(email == null)
        {
            List<ShowUserResponse> showBasicUsers = new ArrayList<>(converter.convertUserListToShowUserResponseCollection(userService.findUsersByRole(roleService.findRoleByName(ERole.ROLE_USER))));
            return ResponseEntity.ok(showBasicUsers);
        }
        else
        {
            User user = userService.findUserByEmail(email);
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
