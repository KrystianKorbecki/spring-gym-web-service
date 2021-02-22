package com.api.gym.repository.implementation;

import com.api.gym.enums.ERole;
import com.api.gym.exception.UserNotFoundException;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.repository.UserRepository;
import com.api.gym.converters.Converter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public User findUserByProfileName(String profileName)
    {
        return userRepository.findUserByProfileName(profileName).orElseThrow(UserNotFoundException::new);
    }

    public Boolean existsByProfileName(String profileName)
    {
        return userRepository.existsByProfileName(profileName);
    }

    public boolean deleteByProfileName(String profileName)
    {
        return userRepository.deleteByProfileName(profileName);
    }

    public Boolean existsByConfirmationCode(String confirmationCode)
    {
        return userRepository.existsByConfirmationCode(confirmationCode);
    }


    public User findByConfirmationCode(String confirmationCode)
    {
        return userRepository.findUserByConfirmationCode(confirmationCode).orElseThrow(UserNotFoundException::new);
    }

    public Page<User> findAllUsersByRole(int pageNumber, int pageSize, Role role)
    {
        Pageable pageable = PageRequest.of(pageNumber - 1 , pageSize);
        return userRepository.findAllByRolesIn(converter.convertRoleToRolesSet(role), pageable);
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
