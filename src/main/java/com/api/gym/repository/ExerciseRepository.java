package com.api.gym.repository;

import com.api.gym.models.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;


public interface ExerciseRepository extends JpaRepository<Exercise, Integer>
{
    @Query(value = "SELECT * FROM exercise", nativeQuery = true)
    List<Exercise> findAll();

    Exercise findByName(String name);
}
