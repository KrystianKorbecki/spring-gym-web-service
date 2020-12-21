package com.api.gym.exception;

public class BadDataException extends RuntimeException
{
    public BadDataException()
    {
        super("Error: Wrong data");
    }
}
