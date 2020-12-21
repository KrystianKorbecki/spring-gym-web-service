package com.api.gym.controllers.admin;

import com.api.gym.enums.ERole;
import com.api.gym.payload.request.ChangeActive;
import com.api.gym.payload.request.EmailRequest;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.converters.Converter;
import com.api.gym.service.repository.RoleService;
import com.api.gym.service.repository.UserService;
import com.api.gym.service.users.UsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminUsersController
{
    private final UsersService usersService;
    private final UserService userService;
    private final Converter converter;
    private final RoleService roleService;


    AdminUsersController(UsersService usersService, UserService userService, Converter converter, RoleService roleService)
    {
        this.usersService = usersService;
        this.userService = userService;
        this.converter = converter;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showUsers(@RequestParam(value = "email", required = false) String email)
    {
        if(email == null)
        {
            List<ShowUserResponse> showUsers = new ArrayList<>(converter.convertUserListToShowUserResponseList(userService.findUsersByRole(roleService.findRoleByName(ERole.ROLE_USER))));
            return ResponseEntity.ok(showUsers);
        }
        else
        {
            ShowUserResponse showUserResponse = converter.convertUserToShowUserResponse(userService.findUserByEmail(email));
            return ResponseEntity.ok(showUserResponse);
        }
    }

    @ApiOperation(value = "Change active for trainer")
    @PatchMapping("/user/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>setActiveValue(@Valid @RequestBody ChangeActive changeActive)
    {
        return usersService.changeActive(changeActive);
    }

    @ApiOperation(value = "Change active for trainer")
    @DeleteMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>deleteUser(@Valid @RequestBody EmailRequest emailRequest)
    {
        return ResponseEntity.ok(userService.deleteByEmail(emailRequest.getEmail()));
    }
}
