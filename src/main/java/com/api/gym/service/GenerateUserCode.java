package com.api.gym.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateUserCode
{
    private Random random = new Random();

    public String Generate(int size)
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
