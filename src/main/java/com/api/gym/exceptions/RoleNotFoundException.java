package com.api.gym.exceptions;

public class RoleNotFoundException extends RuntimeException
{
    public RoleNotFoundException(String role)
    {
        super("Error: Role is not found: " + role);
    }
}
