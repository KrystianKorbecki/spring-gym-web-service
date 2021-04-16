package com.api.gym.repository.implementation;

import com.api.gym.exception.DatabaseException;
import com.api.gym.repository.CompleteTrainingPlanRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class CompleteTrainingPlanService
{
    private final CompleteTrainingPlanRepository completeTrainingPlanRepository;

    public CompleteTrainingPlanService(CompleteTrainingPlanRepository completeTrainingPlanRepository)
    {
        this.completeTrainingPlanRepository = completeTrainingPlanRepository;
    }


    public Integer countAllByEndDateEquals(Timestamp date)
    {
        try
        {
            return completeTrainingPlanRepository.countAllByEndDateEquals(date);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
    }

    public Integer countAllByEndDateBetween(Timestamp startDate, Timestamp endDate)
    {
        try
        {
            return completeTrainingPlanRepository.countAllByEndDateBetween(startDate, endDate);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
    }
}
