package com.api.gym.repository;

import com.api.gym.models.TrainingPlan;
import com.api.gym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingPlanRepository extends JpaRepository<TrainingPlan, Long>
{
    List<TrainingPlan> findAllByUser(User user);
    TrainingPlan findByUserAndTrainerAndDayOfWeek(User user, User trainer, String dayOfWeek);
    TrainingPlan findByUserAndDayOfWeek(User user, String dayOfWeek);
}
