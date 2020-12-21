package com.api.gym.controllers.trainer;

import com.api.gym.models.Exercise;
import com.api.gym.payload.request.ExerciseRequest;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.repository.ExerciseRepository;
import com.api.gym.service.repository.ExerciseService;
import com.api.gym.service.repository.ScheduleService;
import com.api.gym.service.repository.UserService;
import com.api.gym.service.users.UsersService;
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

    private final UserService userService;
    private final ScheduleService scheduleService;
    private final UsersService usersService;
    private final ExerciseService exerciseService;
    TrainerController(UserService userService, ScheduleService scheduleService, UsersService usersService, ExerciseService exerciseService)
    {
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.usersService = usersService;
        this.exerciseService = exerciseService;
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
        return ResponseEntity.ok(scheduleService.findScheduleByUser(userService.findUserByEmail(usersService.userDetails().getEmail())));
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showProfile()
    {
        return ResponseEntity.ok(userService.findUserByEmail(usersService.userDetails().getEmail()));
    }

    @PatchMapping("/profile")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> editProfile(@Valid @RequestBody SignupRequest signupRequest)
    {
        usersService.changeUserProfile(signupRequest);
        return ResponseEntity.ok(new MessageResponse("Changed"));
    }

    @GetMapping("/exercise")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showExercises()
    {
        return ResponseEntity.ok(exerciseService.findAll());
    }

    @PostMapping("/exercise")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> createExercise(@Valid @RequestBody ExerciseRequest exerciseRequest)
    {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseRequest.getName());
        exercise.setDescription(exerciseRequest.getDescription());
        exerciseService.save(exercise);
        return ResponseEntity.ok(new MessageResponse("Create Successfully!"));
    }
}
