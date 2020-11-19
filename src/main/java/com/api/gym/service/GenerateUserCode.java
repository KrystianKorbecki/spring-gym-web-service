package com.api.gym.service;

import com.api.gym.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GenerateUserCode
{
    UserRepository userRepository;
    GenerateUserCode(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }
    private Random random = new Random();

    public String Generate(int size) throws NullPointerException
    {
        String asciiValue = "";
        if(userRepository != null)
        {
            generateCode(size);
            if(userRepository.existsByCode(asciiValue))
            {
                Generate(size);
                return "";
            }
            else
                return asciiValue;
        }

        else
            return generateCode(size);
    }

    public String generateCode(int size)
    {
        String asciiValue = "";
        for(int i = 0; i < size; i++)
        {
            int decimalValue = random.nextInt(74)+48;
            asciiValue = asciiValue + Character.toString((char) decimalValue);
        }
        return asciiValue;
    }
}
