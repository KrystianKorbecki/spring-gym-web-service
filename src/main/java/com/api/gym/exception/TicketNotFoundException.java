package com.api.gym.exception;

public class TicketNotFoundException extends RuntimeException
{
    public TicketNotFoundException(Long id)
    {
        super("Error: Ticket is not found by id: " + id);
    }
}
