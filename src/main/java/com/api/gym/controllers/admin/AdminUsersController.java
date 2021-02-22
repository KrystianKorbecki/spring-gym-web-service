package com.api.gym.controllers.admin;

import com.api.gym.enums.ERole;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.converters.Converter;
import com.api.gym.repository.implementation.RoleService;
import com.api.gym.repository.implementation.UserService;
import com.api.gym.service.users.UsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/users/{pageNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showBasicUsers(@PathVariable int pageNumber, @RequestParam(required = false) int pageSize)
    {
        if(pageSize == 0)
        {
            pageSize = 20;
        }
        List<ShowUserResponse> showUsers = new ArrayList<>(converter.convertUserListToShowUserResponseList(userService.findAllUsersByRole(pageNumber, pageSize, roleService.findRoleByName(ERole.ROLE_USER)).getContent()));
        return ResponseEntity.ok(showUsers);
    }

    @GetMapping("/user/{profileName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showBasicUser(@PathVariable String profileName)
    {
        ShowUserResponse showUserResponse = converter.convertUserToShowUserResponse(userService.findUserByProfileName(profileName));
        return ResponseEntity.ok(showUserResponse);
    }

    @ApiOperation(value = "Change active for user")
    @PatchMapping("/user/{profileName}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setActiveValue(@PathVariable String profileName)
    {
        return usersService.changeActive(profileName);
    }

    @ApiOperation(value = "Delete user")
    @DeleteMapping("/user/{profileName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String profileName)
    {
        userService.deleteByProfileName(profileName);
        return ResponseEntity.ok().build();
    }
}
