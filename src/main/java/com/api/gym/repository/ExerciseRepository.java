package com.api.gym.repository;

import com.api.gym.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ExerciseRepository extends JpaRepository<Exercise, Integer>
{
    Optional<List<Exercise>> findAllByNameNotNull();

    Optional<Exercise> findByName(String name);

}
