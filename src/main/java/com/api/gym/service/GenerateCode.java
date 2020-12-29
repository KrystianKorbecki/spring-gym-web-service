package com.api.gym.service;

import com.api.gym.repository.UserRepository;
import com.api.gym.service.repository.UserService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@NoArgsConstructor
public class GenerateCode
{

    private UserService userService;
    GenerateCode(UserService userService)
    {
        this.userService = userService;
    }

    public String generateCode()
    {
        String code = UUID.randomUUID().toString().replace("-", "");
        if(userService == null)
        {
            return code;
        }
        else if(userService.existsByConfirmationCode(code))
        {
            generateCode();
            return "";
        }
        else
        {
            return code;
        }
    }
}
