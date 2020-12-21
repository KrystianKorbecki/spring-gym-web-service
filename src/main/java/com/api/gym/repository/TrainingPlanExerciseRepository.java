package com.api.gym.repository;

import com.api.gym.models.TrainingPlan;
import com.api.gym.models.TrainingPlanExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingPlanExerciseRepository extends JpaRepository<TrainingPlanExercise,Long>
{
    <S extends TrainingPlanExercise> S save(S entity);
    Optional<List<TrainingPlanExercise>> findAllByTrainingPlan(TrainingPlan trainingPlan);
}
