package com.api.gym.service.users;

import com.api.gym.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminsService
{
    UserRepository userRepository;
    AdminsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


}
