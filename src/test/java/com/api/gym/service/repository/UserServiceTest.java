package com.api.gym.service.repository;


import com.api.gym.TestDataPrepare;
import com.api.gym.enums.EGender;
import com.api.gym.enums.ERole;
import com.api.gym.exception.UserNotFoundException;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.repository.UserRepository;
import com.api.gym.repository.implementation.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest
{
    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    TestDataPrepare testDataPrepare = new TestDataPrepare();

    @Test
    public void when_enter_email_then_user_will_be_found()
    {
        //given
        Optional<User> userOptional =Optional.of(testDataPrepare.getUser());
        //when
        Mockito.when(userRepository.findUserByEmail(testDataPrepare.getUser().getEmail())).thenReturn(userOptional);
        //then
        Assert.assertEquals(testDataPrepare.getUser().getEmail(), userService.findUserByEmail(testDataPrepare.getUser().getEmail()).getEmail());
        Assert.assertThrows(UserNotFoundException.class, ()-> userService.findUserByEmail(testDataPrepare.getUser().getEmail() + "t"));
    }

    @Test
    public void when_enter_roles_then_users_will_be_found()
    {
        //given
        Optional<List<User>> listOfOptionalAdminUser = testDataPrepare.optionalUserList(testDataPrepare.getAdmin());
        Optional<List<User>> listOfOptionalUser = testDataPrepare.optionalUserList(testDataPrepare.getUser());
        Optional<List<User>> listOfOptionalModeratorUser = testDataPrepare.optionalUserList(testDataPrepare.getModerator());
        Optional<List<User>> listOfOptionalSuperAdminUser = testDataPrepare.optionalUserList(testDataPrepare.getSuperAdmin());
        Optional<List<User>> listOfOptionalTrainerUser = testDataPrepare.optionalUserList(testDataPrepare.getTrainer());
        Optional<List<User>> listOfOptionalBasicUser = testDataPrepare.optionalUserList(testDataPrepare.getBasic());

        //when
        Mockito.when(userRepository.findUsersByRolesIn(testDataPrepare.getAdminRoleSet())).thenReturn(listOfOptionalAdminUser);
        Mockito.when(userRepository.findUsersByRolesIn(testDataPrepare.getUserRoleSet())).thenReturn(listOfOptionalUser);
        Mockito.when(userRepository.findUsersByRolesIn(testDataPrepare.getModeratorRoleSet())).thenReturn(listOfOptionalModeratorUser);
        Mockito.when(userRepository.findUsersByRolesIn(testDataPrepare.getSuperAdminRoleSet())).thenReturn(listOfOptionalSuperAdminUser);
        Mockito.when(userRepository.findUsersByRolesIn(testDataPrepare.getTrainerRoleSet())).thenReturn(listOfOptionalTrainerUser);
        Mockito.when(userRepository.findUsersByRolesIn(testDataPrepare.getBasicRoleSet())).thenReturn(listOfOptionalBasicUser);

        //then
        Assert.assertEquals(listOfOptionalAdminUser.get(), userService.findAllUsersByRoles(testDataPrepare.getAdminRoleSet()));
        Assert.assertEquals(listOfOptionalUser.get(), userService.findAllUsersByRoles(testDataPrepare.getUserRoleSet()));
        Assert.assertEquals(listOfOptionalModeratorUser.get(), userService.findAllUsersByRoles(testDataPrepare.getModeratorRoleSet()));
        Assert.assertEquals(listOfOptionalSuperAdminUser.get(), userService.findAllUsersByRoles(testDataPrepare.getSuperAdminRoleSet()));
        Assert.assertEquals(listOfOptionalTrainerUser.get(), userService.findAllUsersByRoles(testDataPrepare.getTrainerRoleSet()));
        Assert.assertEquals(listOfOptionalBasicUser.get(), userService.findAllUsersByRoles(testDataPrepare.getBasicRoleSet()));
    }

    @Test
    public void when_enter_roles_then_throw_exception()
    {
        //given
        Optional<List<User>> emptyOptionalList = Optional.empty();
        //when
        Mockito.when(userRepository.findUsersByRolesIn(testDataPrepare.getAdminRoleSet())).thenReturn(emptyOptionalList);
        //then
        Assert.assertThrows(UserNotFoundException.class, ()-> userService.findAllUsersByRoles(testDataPrepare.getAdminRoleSet()));
        Assert.assertThrows(UserNotFoundException.class, ()-> userService.findAllUsersByRoles(testDataPrepare.getTrainerRoleSet()));

    }

    @Test
    public void when_enter_email_then_return_true()
    {
        //when
        Mockito.when(userRepository.existsByEmail(testDataPrepare.getUser().getEmail())).thenReturn(true);

        //then
        Assert.assertEquals(true, userService.existsByEmail(testDataPrepare.getUser().getEmail()));
    }

    @Test
    public void when_enter_email_then_return_false()
    {
        //when
        Mockito.when(userRepository.existsByEmail(testDataPrepare.getAdmin().getEmail())).thenReturn(false);

        //then
        Assert.assertEquals(false, userService.existsByEmail(testDataPrepare.getAdmin().getEmail()));
        Assert.assertEquals(false, userService.existsByEmail(testDataPrepare.getTrainer().getEmail()));
    }

    @Test
    public void when_enter_confirmation_code_then_user_will_be_found()
    {
        //given
        Optional<User> adminOptional = Optional.of(testDataPrepare.getAdmin());

        //when
        Mockito.when(userRepository.findUserByConfirmationCode(testDataPrepare.getAdmin().getConfirmationCode())).thenReturn(adminOptional);

        //then
        Assert.assertEquals(testDataPrepare.getAdmin(), userService.findByConfirmationCode(testDataPrepare.getAdmin().getConfirmationCode()));
    }

    @Test
    public void when_enter_confirmation_code_then_throw_exception()
    {
        Optional<User> emptyOptional = Optional.empty();

        //when
        Mockito.when(userRepository.findUserByConfirmationCode(testDataPrepare.getAdmin().getConfirmationCode())).thenReturn(emptyOptional);

        //then
        Assert.assertThrows(UserNotFoundException.class, () -> userService.findByConfirmationCode(testDataPrepare.getAdmin().getConfirmationCode()));
        Assert.assertThrows(UserNotFoundException.class, () -> userService.findByConfirmationCode(testDataPrepare.getUser().getConfirmationCode()));
    }

}
