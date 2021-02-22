package com.api.gym.repository.implementation;

import com.api.gym.exception.ExerciseNotFoundException;
import com.api.gym.models.Exercise;
import com.api.gym.repository.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ExerciseService
{
    private final ExerciseRepository exerciseRepository;

    ExerciseService(ExerciseRepository exerciseRepository)
    {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> findAll()
    {
        return exerciseRepository.findAllByNameNotNull().orElseThrow(ExerciseNotFoundException::new);
    }

    public Exercise findByName(String name)
    {
        return exerciseRepository.findByName(name).orElseThrow(() -> new ExerciseNotFoundException(name));
    }

    public <S extends Exercise> S save(S entity)
    {
        return exerciseRepository.save(entity);
    }

}
