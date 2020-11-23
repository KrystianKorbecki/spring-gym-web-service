package com.api.gym.service.repository;

import com.api.gym.repository.ExerciseRepository;
import org.springframework.stereotype.Service;


@Service
public class ExerciseRepositoryService
{
    private final ExerciseRepository exerciseRepository;

    ExerciseRepositoryService(ExerciseRepository exerciseRepository)
    {
        this.exerciseRepository = exerciseRepository;
    }


}
