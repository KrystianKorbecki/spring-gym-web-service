package com.api.gym.repository.implementation;

import com.api.gym.enums.ERole;
import com.api.gym.exception.DatabaseException;
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

import java.sql.SQLException;
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

    public Integer countUserByCreateDate(Date date)
    {
        try
        {
            return userRepository.countUserByCreateDate(date);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
    }

    public Integer countUsersByRolesIn(Set<Role> roles)
    {
        try
        {
            return userRepository.countUsersByRolesIn(roles);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
    }

    public Integer countUserByCreateDateBetween(Date startDate, Date endDate)
    {
        try
        {
            return userRepository.countUserByCreateDateBetween(startDate, endDate);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
    }

    public User findUserByProfileName(String profileName)
    {
        return userRepository.findUserByProfileName(profileName).orElseThrow(UserNotFoundException::new);
    }

    public Boolean existsByProfileName(String profileName)
    {
        try
        {
            return userRepository.existsByProfileName(profileName);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
    }

    public boolean deleteByProfileName(String profileName)
    {
        try
        {
            return userRepository.deleteByProfileName(profileName);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }

    }

    public Boolean existsByConfirmationCode(String confirmationCode)
    {
        try
        {
            return userRepository.existsByConfirmationCode(confirmationCode);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
    }


    public User findByConfirmationCode(String confirmationCode)
    {
        return userRepository.findUserByConfirmationCode(confirmationCode).orElseThrow(UserNotFoundException::new);
    }

    public Page<User> findAllUsersByRole(int pageNumber, int pageSize, Role role)
    {
        try
        {
            Pageable pageable = PageRequest.of(pageNumber - 1 , pageSize);
            return userRepository.findAllByRolesIn(converter.convertRoleToRolesSet(role), pageable);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
    }

    public Boolean existsByEmail(String email)
    {
        try
        {
            return userRepository.existsByEmail(email);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
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
        try
        {
            userRepository.save(entity);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }

    }

    public List<User> findAllByRolesAndIdTrainer(Set<Role> roles, Long idTrainer)
    {
        return userRepository.findUsersByRolesInAndIdTrainer(roles, idTrainer).orElseThrow(() -> new UserNotFoundException(roles));
    }

    public List<User> findUsersByRole(Role role)
    {
        try{
            return findAllUsersByRoles(converter.convertRoleToRolesSet(role));
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
    }


}
