package com.api.gym.repository.implementation;

import com.api.gym.enums.ERole;
import com.api.gym.exception.RoleNotFoundException;
import com.api.gym.models.Role;
import com.api.gym.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService
{
    private final RoleRepository roleRepository;

    RoleService(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(ERole role)
    {
        return roleRepository.findByName(role).orElseThrow(() -> new RoleNotFoundException(role.toString()));
    }


//    public Set<Role> findAllByNameIn(Set<ERole> roles)
//    {
//        return roleRepository.findRolesByNameIn(roles).orElseThrow(() -> new RoleNotFoundException(roles));
//    }



}
