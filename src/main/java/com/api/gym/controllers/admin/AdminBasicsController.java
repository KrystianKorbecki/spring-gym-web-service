package com.api.gym.controllers.admin;

import com.api.gym.enums.ERole;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangeRole;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.converters.Converter;
import com.api.gym.repository.implementation.RoleService;
import com.api.gym.repository.implementation.UserService;
import com.api.gym.security.services.UserDetailsImpl;
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


    /**
     * This method show list of users with {@link ERole#ROLE_BASIC} for users with {@link ERole#ROLE_ADMIN}
     * @param pageNumber contains number of page
     * @param pageSize contains size of page
     * @return {@link List} of {@link ShowUserResponse}, who shows main data about users with {@link ERole#ROLE_BASIC}
     */
    @GetMapping("/basics/{pageNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ShowUserResponse>> showBasicUsers(@PathVariable int pageNumber, @RequestParam(required = false) int pageSize)
    {
        if(pageSize == 0)
        {
            pageSize = 20;
        }

        List<ShowUserResponse> showBasicUsers = new ArrayList<>(converter.convertUserListToShowUserResponseCollection(userService.findAllUsersByRole(pageNumber, pageSize, roleService.findRoleByName(ERole.ROLE_BASIC)).getContent()));
        return ResponseEntity.ok(showBasicUsers);
    }

    @GetMapping("/basic/{profileName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showBasicUser(@PathVariable String profileName)
    {
        User user = userService.findUserByProfileName(profileName);
        return ResponseEntity.ok(user);
    }


    @PatchMapping("/basic/{profileName}/privilages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeRole(@PathVariable String profileName, @Valid @RequestBody ChangeRole changeRole)
    {
        return new ResponseEntity(usersService.changeRoleByProfileName(profileName, changeRole, usersService.userDetails()), HttpStatus.ACCEPTED);
    }
}
