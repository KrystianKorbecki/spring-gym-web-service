package com.api.gym.exception;

public class ExerciseNotFoundException extends RuntimeException
{
    public ExerciseNotFoundException(String name)
    {
        super("Error: Exercise not found: " + name);
    }

    public ExerciseNotFoundException()
    {
        super("Error: Database is empty");
    }
}
