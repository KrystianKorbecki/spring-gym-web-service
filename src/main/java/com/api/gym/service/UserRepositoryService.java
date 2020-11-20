package com.api.gym.service;

import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class UserRepositoryService
{
    private final UserRepository userRepository;

    public Boolean deleteByEmail(String email)
    {
        return userRepository.deleteByEmail(email);
    }

    UserRepositoryService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }

    public List<User> findAllUsersByRoles(Set<Role>roles)
    {
        return userRepository.findAllByRolesIn(roles);
    }

    public <S extends User> void saveUser(S entity)
    {
        userRepository.save(entity);
    }

}