package com.api.gym.controllers.trainer;

import com.api.gym.enums.ERole;
import com.api.gym.models.*;
import com.api.gym.payload.request.ChangeActive;
import com.api.gym.payload.request.EmailRequest;
import com.api.gym.payload.request.TrainingPlanCreateRequest;
import com.api.gym.payload.request.TrainingPlanExerciseRequest;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.repository.ExerciseRepository;
import com.api.gym.repository.TrainingPlanExerciseRepository;
import com.api.gym.repository.TrainingPlanRepository;
import com.api.gym.service.repository.RoleRepositoryService;
import com.api.gym.service.repository.UserRepositoryService;
import com.api.gym.service.users.UsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    private final UserRepositoryService userRepositoryService;
    private final RoleRepositoryService roleRepositoryService;
    private final TrainingPlanRepository trainingPlanRepository;
    private final ExerciseRepository exerciseRepository;
    private final TrainingPlanExerciseRepository trainingPlanExerciseRepository;

    TrainerUsersController(UsersService usersService, UserRepositoryService userRepositoryService, TrainingPlanRepository trainingPlanRepository, ExerciseRepository exerciseRepository, TrainingPlanExerciseRepository trainingPlanExerciseRepository,RoleRepositoryService roleRepositoryService)
    {
        this.usersService = usersService;
        this.userRepositoryService = userRepositoryService;
        this.trainingPlanRepository = trainingPlanRepository;
        this.exerciseRepository = exerciseRepository;
        this.trainingPlanExerciseRepository = trainingPlanExerciseRepository;
        this.roleRepositoryService = roleRepositoryService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showMyUser(@RequestParam(value = "email", required = false) String email)
    {
        if(email == null)
        {
            //List<ShowUserResponse> showUsers = new ArrayList<>(usersService.findUsersByRole(ERole.ROLE_USER));
            Set<Role> roles = usersService.findRole(ERole.ROLE_USER);
            List<User> userList = new ArrayList<>(userRepositoryService.findAllByRolesAndIdTrainer(roles, usersService.userDetails().getId()));
            return ResponseEntity.ok(userList);
        }
        else
        {
            User user = userRepositoryService.findUserByEmail(email);
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping("/user")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?>deleteUser(@Valid @RequestBody EmailRequest emailRequest)
    {
        return ResponseEntity.ok(userRepositoryService.deleteByEmail(emailRequest.getEmail()));
    }

    @ApiOperation(value = "Change active for user")
    @PatchMapping("/user/active")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?>setActiveValue(@Valid @RequestBody ChangeActive changeActive)
    {
        return usersService.changeActive(changeActive);
    }

    @GetMapping("/user/training")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showTrainingPlansForUser(@RequestParam(value = "email", required = false) String email, @RequestParam(value = "dayOfWeek", required = false) String dayOfWeek)
    {
        if(email != null && dayOfWeek != null)
        {
            return ResponseEntity.ok(trainingPlanRepository.findByUserAndDayOfWeek(userRepositoryService.findUserByEmail(email), dayOfWeek));
        }
        else
        {
            return ResponseEntity.ok(trainingPlanRepository.findAllByUser(userRepositoryService.findUserByEmail(email)));
        }

    }

    @PostMapping("/user/training")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> createNewTrainingPlan(@Valid @RequestBody TrainingPlanCreateRequest trainingPlanCreateRequest)
    {
        User user = userRepositoryService.findUserByEmail(trainingPlanCreateRequest.getUserEmail());
        User trainer = userRepositoryService.findUserByEmail(usersService.userDetails().getEmail());
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setTrainer(trainer);
        trainingPlan.setUser(user);
        trainingPlan.setDayOfWeek(trainingPlanCreateRequest.getDayOfWeek());
        trainingPlan.setProposeRestBetweenExercises(trainingPlanCreateRequest.getProposeRestBetweenExercises());
        trainingPlanRepository.save(trainingPlan);


        List<TrainingPlanExerciseRequest> trainingPlanExerciseRequests = new ArrayList<>(trainingPlanCreateRequest.getTrainingPlanExerciseRequests());
        for(TrainingPlanExerciseRequest training:trainingPlanExerciseRequests)
        {
            TrainingPlanExercise trainingPlanExercise = new TrainingPlanExercise();
            trainingPlanExercise.setDescription(training.getDescription());
            trainingPlanExercise.setExercise(exerciseRepository.findByName(training.getNameExercise()));
            trainingPlanExercise.setProposeRepeat(training.getProposeRepeat());
            trainingPlanExercise.setProposeRest(training.getProposeRest());
            trainingPlanExercise.setProposeSeries(training.getProposeSeries());
            trainingPlanExercise.setProposeWeight(training.getProposeWeight());
            trainingPlanExercise.setTrainingPlan(trainingPlanRepository.findByUserAndTrainerAndDayOfWeek(user,trainer,trainingPlanCreateRequest.getDayOfWeek()));
            trainingPlanExerciseRepository.save(trainingPlanExercise);
        }
        return ResponseEntity.ok(new MessageResponse("Successfully save"));
    }


}
