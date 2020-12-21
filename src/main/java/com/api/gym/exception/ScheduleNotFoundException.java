package com.api.gym.exception;

import java.time.LocalDateTime;

public class ScheduleNotFoundException extends RuntimeException
{
    public ScheduleNotFoundException(LocalDateTime startDate, LocalDateTime endDate, String email)
    {
        super("Error: Schedule with start date: " + startDate + " and end date: " + endDate + " by email: " + email + "not found.");
    }

    public ScheduleNotFoundException(String email)
    {
        super("Error: Schedule by email: " + email + " not found.");
    }
}
