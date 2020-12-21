package com.api.gym.service.repository;

import com.api.gym.exception.TrainingPlanNotFoundException;
import com.api.gym.models.TrainingPlan;
import com.api.gym.models.User;
import com.api.gym.repository.TrainingPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingPlanService
{
    private final TrainingPlanRepository trainingPlanRepository;

    TrainingPlanService(TrainingPlanRepository trainingPlanRepository)
    {
        this.trainingPlanRepository = trainingPlanRepository;
    }

    public List<TrainingPlan> findAllByUser(User user)
    {
        return trainingPlanRepository.findAllByUser(user).orElseThrow(() -> new TrainingPlanNotFoundException(user.getUserName(), user.getLastName(), user.getEmail()));
    }

    public TrainingPlan findByUserAndTrainerAndDayOfWeek(User user, User trainer, String dayOfWeek)
    {
        return trainingPlanRepository.findByUserAndTrainerAndDayOfWeek(user, trainer, dayOfWeek).orElseThrow(() -> new TrainingPlanNotFoundException(user.getUserName(), user.getLastName(), user.getEmail()));
    }

    public TrainingPlan findByUserAndDayOfWeek(User user, String dayOfWeek)
    {
        return trainingPlanRepository.findByUserAndDayOfWeek(user, dayOfWeek).orElseThrow(() -> new TrainingPlanNotFoundException(user.getUserName(), user.getLastName(), user.getEmail()));
    }


    public  <S extends TrainingPlan> S save(S entity)
    {

        return trainingPlanRepository.save(entity);
    }
}
