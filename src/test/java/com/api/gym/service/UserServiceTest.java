package com.api.gym.service;

import com.api.gym.entity.Role;
import com.api.gym.entity.User;
import com.api.gym.repository.RoleRepository;
import com.api.gym.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest
{
//    @Test
//    public void should_register_user()
//    {
//        //given
//        UserService userService = mock(UserService.class);
//        User user = new User();
//        user.setRoles("ADMIN");
//        when(userService.createUser(user, "ADMIN")).thenReturn("User registered successfully");
//        //then
//        Assert.assertThat(userService.showAllUsers("ADMIN"), Matchers.hasSize(1));
//        Assert.assertArrayEquals("User registered successfully", userService.createUser(user, "ADMIN"));
//    }
}
