package com.api.gym.controllers.trainer;

import com.api.gym.models.ERole;
import com.api.gym.models.User;
import com.api.gym.payload.request.EmailRequest;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.service.ScheduleRepositoryService;
import com.api.gym.service.UserRepositoryService;
import com.api.gym.service.UsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/trainer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrainerController
{

    private final UserRepositoryService userRepositoryService;
    private final ScheduleRepositoryService scheduleRepositoryService;
    private final UsersService usersService;
    TrainerController(UserRepositoryService userRepositoryService, ScheduleRepositoryService scheduleRepositoryService, UsersService usersService)
    {
        this.userRepositoryService = userRepositoryService;
        this.scheduleRepositoryService = scheduleRepositoryService;
        this.usersService = usersService;
    }

    @ApiOperation(value = "Show main site for trainer")
    @GetMapping
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<Map<String,String>>trainerMainSite()
    {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("newUsersToday", "1000");
        response.put("soldTicketsToday", "100");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/schedule")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showSchedule()
    {
        return ResponseEntity.ok(scheduleRepositoryService.findScheduleByUser(userRepositoryService.findUserByEmail(usersService.userDetails().getEmail())));
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showProfile()
    {
        return ResponseEntity.ok(userRepositoryService.findUserByEmail(usersService.userDetails().getEmail()));
    }

    @PatchMapping("/profile")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> editProfile(@Valid @RequestBody SignupRequest signupRequest)
    {
        usersService.changeUserProfile(signupRequest);
        return ResponseEntity.ok(new MessageResponse("Changed"));
    }
}