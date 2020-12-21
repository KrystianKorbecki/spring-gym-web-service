package com.api.gym.exception;

import com.api.gym.models.Role;

import java.util.Set;

public class RoleNotFoundException extends RuntimeException
{
    public RoleNotFoundException(String role)
    {
        super("Error: Role is not found: " + role);
    }

    public RoleNotFoundException(Set<Role> roles)
    {
        super("Error: Roles are not found: " + roles.toString());
    }
}
