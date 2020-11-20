package com.api.gym.service;

import com.api.gym.models.ERole;
import com.api.gym.models.Role;
import com.api.gym.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleRepositoryService
{
    private final RoleRepository roleRepository;

    RoleRepositoryService(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findRoleByName(ERole role)
    {
        return roleRepository.findByName(role);
    }
}
