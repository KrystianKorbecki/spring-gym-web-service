package com.api.gym.repository;

import com.api.gym.models.CompleteExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompleteExerciseRepository extends JpaRepository<CompleteExercise, Long> {
}
