package com.api.gym.service;

import com.api.gym.entity.Role;
import com.api.gym.entity.User;
import com.api.gym.model.UserRequest;
import com.api.gym.repository.RoleRepository;
import com.api.gym.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@NoArgsConstructor
@Service
public class RegistrationService
{
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private  RoleRepository roleRepository;
    private  UserRepository userRepository;
    private final GenerateRandomChar generateRandomChar = new GenerateRandomChar();

    @Autowired
    public RegistrationService(BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, UserRepository userRepository)
    {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }



    public String createUser(UserRequest userRequest)
    {
        User user = new User();
        User userExist = userRepository.findByEmailAddress(userRequest.getEmailAddress());
        if(userExist != null)
        {
            return "Email addres is in use.";
        }
        else
        {
            Date date = new Date(new java.util.Date().getTime());
            Role userRole = roleRepository.findByName(userRequest.getRole());
            user.setName(userRequest.getName());
            user.setLastName(userRequest.getLastName());
            user.setPhoneNumber(userRequest.getPhoneNumber());
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            user.setEmailAddress(userRequest.getEmailAddress());
            user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
            user.setCode(generateRandomChar.Generate(6));
            user.setCreateDate(date);
            userRepository.save(user);
            return"User registered successfully";
        }

    }

    public List<User> showAllUsers(String role)
    {
        return (List<User>) userRepository.findAllByRoles(role);
    }
}
