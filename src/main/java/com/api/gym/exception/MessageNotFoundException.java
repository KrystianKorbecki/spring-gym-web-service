package com.api.gym.exception;

public class MessageNotFoundException extends RuntimeException
{
    public MessageNotFoundException()
    {
        super("Messages are empty");
    }
}
