package com.api.gym.controllers;

import com.api.gym.converters.Converter;
import com.api.gym.enums.ERole;
import com.api.gym.repository.implementation.ExerciseService;
import com.api.gym.repository.implementation.RoleService;
import com.api.gym.repository.implementation.ScheduleService;
import com.api.gym.repository.implementation.UserService;
import com.api.gym.service.users.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", maxAge = 3600)
public class MainSiteController
{
    private final UserService userService;
    private final RoleService roleService;
    private final Converter converter;

    MainSiteController(UserService userService, RoleService roleService, Converter converter)
    {
        this.userService = userService;
        this.roleService = roleService;
        this.converter = converter;
    }

    @GetMapping("/profile/{profileName}")
    public ResponseEntity<?> showTrainerProfile(@PathVariable String profileName)
    {
        return ResponseEntity.ok(userService.findUserByProfileName(profileName));
    }

    @GetMapping("/profile/{pageNumber}")
    public ResponseEntity<?> showTrainers(@PathVariable int pageNumber, @RequestParam(required = false) int pageSize)
    {
        return ResponseEntity.ok(converter.convertUserListToShowUserResponseList(userService.findAllUsersByRole(pageNumber, pageSize, roleService.findRoleByName(ERole.ROLE_TRAINER)).getContent()));
    }
}
