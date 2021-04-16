package com.api.gym.controllers.trainer;

import com.api.gym.enums.ERole;
import com.api.gym.payload.request.ChangeRole;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.converters.Converter;
import com.api.gym.repository.implementation.RoleService;
import com.api.gym.repository.implementation.UserService;
import com.api.gym.service.users.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/basics/{pageNumber}")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showBasicUsers(@PathVariable int pageNumber, @RequestParam(required = false) int pageSize)
    {
        if(pageSize == 0)
        {
            pageSize = 20;
        }
        List<ShowUserResponse> showBasicUsers = new ArrayList<>(converter.convertUserListToShowUserResponseCollection(userService.findAllUsersByRole(pageNumber, pageSize, roleService.findRoleByName(ERole.ROLE_BASIC)).getContent()));
        return ResponseEntity.ok(showBasicUsers);
    }

    @GetMapping("/basic/{profileName}")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showBasicUsers(@PathVariable String profileName)
    {
        return ResponseEntity.ok(userService.findUserByProfileName(profileName));
    }

    @PatchMapping("/basic/{profileName}/")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> addUser(@PathVariable String profileName)
    {
        ChangeRole changeRole = new ChangeRole();
        changeRole.setAddRoles(converter.convertERoleToERolesSet(ERole.ROLE_USER));
        changeRole.setDeleteRoles(converter.convertERoleToERolesSet(ERole.ROLE_BASIC));
        return new ResponseEntity<>(usersService.changeRoleByProfileName(profileName, changeRole, usersService.userDetails()), HttpStatus.OK);
    }


    @DeleteMapping("/basic/{profileName}")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?>deleteBasic(@PathVariable String profileName)
    {
        userService.deleteByProfileName(profileName);
        return ResponseEntity.ok().build();
    }
}
