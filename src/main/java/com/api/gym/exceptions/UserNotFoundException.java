package com.api.gym.exceptions;

import com.api.gym.models.User;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(User user)
    {
        super("Error: User is not found: " + user);
    }
}
