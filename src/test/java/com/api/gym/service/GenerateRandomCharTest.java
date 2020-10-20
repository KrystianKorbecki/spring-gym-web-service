package com.api.gym.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

class GenerateRandomCharTest
{

    @Test
    public void should_generate_random_string()
    {

        //given
        GenerateRandomChar generateRandomChar = new GenerateRandomChar();
        //then
        Assert.assertNotNull(generateRandomChar.Generate(5));
    }

    @Test
    public void should_no_generate_random_string()
    {
        //given
        GenerateRandomChar generateRandomChar = new GenerateRandomChar();
        //then
        Assert.assertEquals(generateRandomChar.Generate(0), "");
        Assert.assertEquals(generateRandomChar.Generate(-1), "");
    }
}
