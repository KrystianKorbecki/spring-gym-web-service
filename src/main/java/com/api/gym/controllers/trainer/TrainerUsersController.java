package com.api.gym.controllers.trainer;

import com.api.gym.enums.ERole;
import com.api.gym.models.*;
import com.api.gym.payload.request.EmailRequest;
import com.api.gym.payload.request.TrainingPlanCreateRequest;
import com.api.gym.converters.Converter;
import com.api.gym.repository.implementation.*;
import com.api.gym.service.users.TrainersService;
import com.api.gym.service.users.UsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/trainer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrainerUsersController
{
    private final UsersService usersService;
    private final UserService userService;
    private final ExerciseService exerciseService;
    private final TrainingPlanService trainingPlanService;
    private final TrainingPlanExerciseService trainingPlanExerciseService;
    private final RoleService roleService;
    private final Converter converter;
    private final TrainersService trainersService;

    TrainerUsersController(UsersService usersService, UserService userService, ExerciseService exerciseService,TrainingPlanExerciseService trainingPlanExerciseService,
                           TrainingPlanService trainingPlanService, RoleService roleService, Converter converter, TrainersService trainersService)
    {
        this.usersService = usersService;
        this.trainingPlanExerciseService = trainingPlanExerciseService;
        this.userService = userService;
        this.exerciseService = exerciseService;
        this.trainingPlanService = trainingPlanService;
        this.roleService = roleService;
        this.converter = converter;
        this.trainersService = trainersService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showMyUser(@RequestParam(value = "email", required = false) String email)
    {
        if(email == null)
        {
            Set<Role> roles = converter.convertRoleToRolesSet(roleService.findRoleByName(ERole.ROLE_USER));
            List<User> userList = new ArrayList<>(userService.findAllByRolesAndIdTrainer(roles, usersService.userDetails().getId()));
            return ResponseEntity.ok(userList);
        }
        else
        {
            User user = userService.findUserByEmail(email);
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping("/user/{profileName}")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?>deleteUser(@PathVariable String profileName)
    {
        userService.deleteByProfileName(profileName);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Change active for user")
    @PatchMapping("/user/{email}/active")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?>setActiveValue(@PathVariable String email)
    {
        return usersService.changeActive(email);
    }

    @GetMapping("/user/training")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showTrainingPlansForUser(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "dayOfWeek", required = false) String dayOfWeek)
    {
        if(email != null && dayOfWeek != null)
        {
            return ResponseEntity.ok(trainingPlanService.findByUserAndDayOfWeek(userService.findUserByEmail(email), dayOfWeek));
        }
        else
        {
            return ResponseEntity.ok(trainingPlanService.findAllByUser(userService.findUserByEmail(email)));
        }

    }

    @PostMapping("/user/training")
    @PreAuthorize("hasRole('TRAINER')")
    @Transactional
    public ResponseEntity<?> createNewTrainingPlan(@Valid @RequestBody TrainingPlanCreateRequest trainingPlanCreateRequest)
    {
        return ResponseEntity.ok(trainersService.createNewTrainingPlan(trainingPlanCreateRequest));
    }


}
