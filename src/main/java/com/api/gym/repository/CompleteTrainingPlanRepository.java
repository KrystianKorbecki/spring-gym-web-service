package com.api.gym.repository;

import com.api.gym.models.CompleteTrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompleteTrainingPlanRepository extends JpaRepository<CompleteTrainingPlan, Long> {
}
