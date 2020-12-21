package com.api.gym.service.repository;

import com.api.gym.exception.TrainingPlanExerciseNotFoundException;
import com.api.gym.models.TrainingPlan;
import com.api.gym.models.TrainingPlanExercise;
import com.api.gym.repository.TrainingPlanExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingPlanExerciseService
{
    private final TrainingPlanExerciseRepository trainingPlanExerciseRepository;

    TrainingPlanExerciseService(TrainingPlanExerciseRepository trainingPlanExerciseRepository)
    {
        this.trainingPlanExerciseRepository = trainingPlanExerciseRepository;
    }

    public <S extends TrainingPlanExercise> S save(S entity)
    {
        return trainingPlanExerciseRepository.save(entity);
    }

    List<TrainingPlanExercise> findAllByTrainingPlan(TrainingPlan trainingPlan)
    {
        return trainingPlanExerciseRepository.findAllByTrainingPlan(trainingPlan).orElseThrow(TrainingPlanExerciseNotFoundException::new);
    }
}
