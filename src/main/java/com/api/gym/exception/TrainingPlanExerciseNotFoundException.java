package com.api.gym.exception;

import com.api.gym.models.User;

public class TrainingPlanExerciseNotFoundException extends RuntimeException
{
    public TrainingPlanExerciseNotFoundException()
    {
        super("Error: Exercises for training plan do not exist");
    }
}
