package com.api.gym.service.users;

import com.api.gym.payload.response.MainAdminData;
import com.api.gym.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminsService
{
    UserRepository userRepository;
    AdminsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

//    public List<MainAdminData> primaryDataForAdmin()
//    {
//
//    }


}
