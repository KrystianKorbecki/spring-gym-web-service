package com.api.gym.exception;

import java.sql.Timestamp;

public class UserTicketNotFoundException extends RuntimeException
{
    public UserTicketNotFoundException(Timestamp startDate, Timestamp endDate)
    {
        super("Error: User's ticket are not found between date " + startDate + " " + endDate);
    }
}
