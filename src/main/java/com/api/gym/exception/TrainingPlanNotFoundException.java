package com.api.gym.exception;

public class TrainingPlanNotFoundException extends RuntimeException
{
    public TrainingPlanNotFoundException(String name, String surname, String email)
    {
        super("Error: Training plan not found for user: " + name + " " + surname + " " + email);
    }
}
