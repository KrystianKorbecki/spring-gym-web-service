package com.api.gym.service.users;

import com.api.gym.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService
{
    UserRepository userRepository;
    AdminService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


}
