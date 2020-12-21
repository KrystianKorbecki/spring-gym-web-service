package com.api.gym.service.users;

import com.api.gym.exception.BadDataException;
import com.api.gym.models.TrainingPlan;
import com.api.gym.models.TrainingPlanExercise;
import com.api.gym.models.User;
import com.api.gym.payload.request.TrainingPlanCreateRequest;
import com.api.gym.payload.request.TrainingPlanExerciseRequest;
import com.api.gym.service.repository.ExerciseService;
import com.api.gym.service.repository.TrainingPlanExerciseService;
import com.api.gym.service.repository.TrainingPlanService;
import com.api.gym.service.repository.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainersService
{

    private final UserService userService;
    private final TrainingPlanService trainingPlanService;
    private final UsersService usersService;
    private final ExerciseService exerciseService;
    private final TrainingPlanExerciseService trainingPlanExerciseService;

    TrainersService(UserService userService, TrainingPlanService trainingPlanService, UsersService usersService, ExerciseService exerciseService, TrainingPlanExerciseService trainingPlanExerciseService)
    {
        this.userService = userService;
        this.trainingPlanService = trainingPlanService;
        this.usersService = usersService;
        this.exerciseService = exerciseService;
        this.trainingPlanExerciseService = trainingPlanExerciseService;
    }


    public Boolean createNewTrainingPlan(TrainingPlanCreateRequest trainingPlanCreateRequest)
    {
        User user = userService.findUserByEmail(trainingPlanCreateRequest.getUserEmail());
        User trainer = userService.findUserByEmail(usersService.userDetails().getEmail());

        trainingPlanService.save(createTrainingPlan(user, trainer, trainingPlanCreateRequest));


        List<TrainingPlanExerciseRequest> trainingPlanExerciseRequests = new ArrayList<>(trainingPlanCreateRequest.getTrainingPlanExerciseRequests());
        for(TrainingPlanExerciseRequest training:trainingPlanExerciseRequests)
        {
            trainingPlanExerciseService.save(createTrainingPlanExercise(training,user, trainer, trainingPlanCreateRequest.getDayOfWeek()));
        }
        return true;
    }

    public TrainingPlan createTrainingPlan(User user, User trainer, TrainingPlanCreateRequest trainingPlanCreateRequest)
    {
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setTrainer(trainer);
        trainingPlan.setUser(user);
        trainingPlan.setDayOfWeek(trainingPlanCreateRequest.getDayOfWeek());
        trainingPlan.setProposeRestBetweenExercises(trainingPlanCreateRequest.getProposeRestBetweenExercises());
        return trainingPlan;
    }

    public Boolean checkCorrectDataCreateTrainingPlanExercise(int lengthProposeRepeat, int lengthProposeWeight, int lengthProposeRest)
    {
        return (lengthProposeRepeat == lengthProposeWeight) && (lengthProposeWeight == lengthProposeRest - 1);
    }


    public TrainingPlanExercise createTrainingPlanExercise(TrainingPlanExerciseRequest trainingPlanExerciseRequest, User user, User trainer, String dayOfWeek) throws BadDataException
    {
        if (checkCorrectDataCreateTrainingPlanExercise(trainingPlanExerciseRequest.getProposeRepeat().size(), trainingPlanExerciseRequest.getProposeWeight().size(), trainingPlanExerciseRequest.getProposeRest().size())) {
            TrainingPlanExercise trainingPlanExercise = new TrainingPlanExercise();
            trainingPlanExercise.setDescription(trainingPlanExerciseRequest.getDescription());
            trainingPlanExercise.setExercise(exerciseService.findByName(trainingPlanExerciseRequest.getNameExercise()));
            trainingPlanExercise.setProposeRepeat(trainingPlanExerciseRequest.getProposeRepeat());
            trainingPlanExercise.setProposeRest(trainingPlanExerciseRequest.getProposeRest());
            trainingPlanExercise.setProposeWeight(trainingPlanExerciseRequest.getProposeWeight());
            trainingPlanExercise.setTrainingPlan(trainingPlanService.findByUserAndTrainerAndDayOfWeek(user, trainer, dayOfWeek));
            return trainingPlanExercise;
        } else {
            throw new BadDataException();
        }
    }

}
