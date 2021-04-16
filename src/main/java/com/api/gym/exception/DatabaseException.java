package com.api.gym.exception;

public class DatabaseException extends RuntimeException
{
    public DatabaseException()
    {
        super("Database error");
    }
}
