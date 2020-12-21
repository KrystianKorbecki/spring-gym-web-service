package com.api.gym.exception;

import com.api.gym.models.Role;

import java.util.Set;

public class UserNotFoundException extends RuntimeException
{
    public UserNotFoundException(String email)
    {
        super("Error: User is not found by email: " + email);
    }

    public UserNotFoundException()
    {
        super("Error: Database is empty");
    }

    public UserNotFoundException(Set<Role> roles)
    {
        super("Error: Database is empty for user with: " + roles);
    }
}
