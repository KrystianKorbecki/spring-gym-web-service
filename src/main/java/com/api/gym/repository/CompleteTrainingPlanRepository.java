package com.api.gym.repository;

import com.api.gym.models.CompleteTrainingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Repository
public interface CompleteTrainingPlanRepository extends JpaRepository<CompleteTrainingPlan, Long>
{
    Integer countAllByEndDateEquals(Timestamp date);
    Integer countAllByEndDateBetween(Timestamp startDate, Timestamp endDate);
}
