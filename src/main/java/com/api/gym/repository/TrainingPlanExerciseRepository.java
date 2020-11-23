package com.api.gym.repository;

import com.api.gym.models.TrainingPlanExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingPlanExerciseRepository extends JpaRepository<TrainingPlanExercise,Long> {
}
