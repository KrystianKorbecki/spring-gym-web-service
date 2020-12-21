package com.api.gym.repository;

import com.api.gym.models.TrainingPlan;
import com.api.gym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long>
{
    Optional<List<TrainingPlan>> findAllByUser(User user);
    Optional<TrainingPlan> findByUserAndTrainerAndDayOfWeek(User user, User trainer, String dayOfWeek);
    Optional<TrainingPlan> findByUserAndDayOfWeek(User user, String dayOfWeek);

}
