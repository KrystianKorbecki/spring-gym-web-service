package com.api.gym.service.repository;

import com.api.gym.exception.UserNotFoundException;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.repository.UserRepository;
import com.api.gym.converters.Converter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final Converter converter;


    UserService(UserRepository userRepository, Converter converter)
    {
        this.userRepository = userRepository;
        this.converter= converter;
    }

    public Boolean deleteByEmail(String email)
    {
        userRepository.deleteByEmail(email);
        return true;
    }

    public Boolean existsByEmail(String email)
    {
        return userRepository.existsByEmail(email);
    }

    public User findUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email) .orElseThrow(() -> new UserNotFoundException(email));
    }

    public List<User> findAllUsersByRoles(Set<Role>roles)
    {
        return userRepository.findUsersByRolesIn(roles).orElseThrow(UserNotFoundException::new);
    }

    public <S extends User> void saveUser(S entity)
    {
        userRepository.save(entity);
    }

    public List<User> findAllByRolesAndIdTrainer(Set<Role> roles, Long idTrainer)
    {
        return userRepository.findUsersByRolesInAndIdTrainer(roles, idTrainer).orElseThrow(() -> new UserNotFoundException(roles));
    }

    public List<User> findUsersByRole(Role role)
    {
        return findAllUsersByRoles(converter.convertRoleToRolesSet(role));
    }


}
