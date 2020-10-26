package com.api.gym.service;

import com.api.gym.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateUserCode
{
    UserRepository userRepository;
    GenerateUserCode(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    private Random random = new Random();

    public String Generate(int size)
    {
        String asciiValue = "";
        for(int i = 0; i < size; i++)
        {
            int decimalValue = random.nextInt(74)+48;
            asciiValue = asciiValue + Character.toString((char) decimalValue);
        }
        if(userRepository.existsByCode(asciiValue) == true)
        {
            Generate(size);
            return "";
        }

        else
            return asciiValue;
    }
}
