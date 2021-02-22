package com.api.gym.service.repository;


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

    User user = new User();
    User admin = new User();
    User basic = new User();
    User trainer = new User();
    User superAdmin = new User();
    User moderator = new User();
    Set<Role> adminRoleSet = createRoleSet(ERole.ROLE_ADMIN);
    Set<Role> trainerRoleSet = createRoleSet(ERole.ROLE_TRAINER);
    Set<Role> moderatorRoleSet = createRoleSet(ERole.ROLE_MODERATOR);
    Set<Role> superAdminRoleSet = createRoleSet(ERole.ROLE_SUPERADMIN);
    Set<Role> userRoleSet = createRoleSet(ERole.ROLE_USER);
    Set<Role> basicRoleSet = createRoleSet(ERole.ROLE_BASIC);

    @Before
    public void prepareUserData()
    {
        admin = createUser(ERole.ROLE_ADMIN, "admin");
        user = createUser(ERole.ROLE_USER, "user");
        trainer = createUser(ERole.ROLE_TRAINER, "trainer");
        superAdmin = createUser(ERole.ROLE_SUPERADMIN, "superadmin");
        moderator = createUser(ERole.ROLE_MODERATOR, "moderator");
        basic = createUser(ERole.ROLE_BASIC, "basic");
    }
    public Optional<List<User>> optionalUserList(User user)
    {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        Optional<List<User>> listOfOptionalAdminUser = Optional.of(userList);
        return listOfOptionalAdminUser;
    }


    public Set<Role> createRoleSet(ERole eRole)
    {
        Set<Role> roleSet = new HashSet<>();
        Role role = new Role(null, eRole);
        roleSet.add(role);
        return roleSet;
    }

    public User createUser(ERole eRole, String name)
    {
        User user = new User();
        UUID uuid = UUID.randomUUID();
        Random random = new Random();
        user.setPassword("password");
        user.setChatCode(uuid.toString());
        user.setConfirmEmail(true);
        user.setConfirmationCode(uuid.toString());
        user.setUserName(name);
        user.setLastName(name);
        user.setGender("Male");
        user.setActive(true);
        user.setPhoneNumber("123456789");
        user.setRoles(createRoleSet(eRole));
        user.setEmail(name + "@" + name + ".pl");
        user.setId(null);
        return user;
    }

    @Test
    public void when_enter_email_then_user_will_be_found()
    {
        //given
        Optional<User> userOptional =Optional.of(user);
        //when
        Mockito.when(userRepository.findUserByEmail(user.getEmail())).thenReturn(userOptional);
        //then
        Assert.assertEquals(user.getEmail(), userService.findUserByEmail(user.getEmail()).getEmail());
        Assert.assertThrows(UserNotFoundException.class, ()-> userService.findUserByEmail(user.getEmail() + "t"));
    }

    @Test
    public void when_enter_roles_then_users_will_be_found()
    {
        //given
        Optional<List<User>> listOfOptionalAdminUser = optionalUserList(admin);
        Optional<List<User>> listOfOptionalUser = optionalUserList(user);
        Optional<List<User>> listOfOptionalModeratorUser = optionalUserList(moderator);
        Optional<List<User>> listOfOptionalSuperAdminUser = optionalUserList(superAdmin);
        Optional<List<User>> listOfOptionalTrainerUser = optionalUserList(trainer);
        Optional<List<User>> listOfOptionalBasicUser = optionalUserList(basic);

        //when
        Mockito.when(userRepository.findUsersByRolesIn(adminRoleSet)).thenReturn(listOfOptionalAdminUser);
        Mockito.when(userRepository.findUsersByRolesIn(userRoleSet)).thenReturn(listOfOptionalUser);
        Mockito.when(userRepository.findUsersByRolesIn(moderatorRoleSet)).thenReturn(listOfOptionalModeratorUser);
        Mockito.when(userRepository.findUsersByRolesIn(superAdminRoleSet)).thenReturn(listOfOptionalSuperAdminUser);
        Mockito.when(userRepository.findUsersByRolesIn(trainerRoleSet)).thenReturn(listOfOptionalTrainerUser);
        Mockito.when(userRepository.findUsersByRolesIn(basicRoleSet)).thenReturn(listOfOptionalBasicUser);

        //then
        Assert.assertEquals(listOfOptionalAdminUser.get(), userService.findAllUsersByRoles(adminRoleSet));
        Assert.assertEquals(listOfOptionalUser.get(), userService.findAllUsersByRoles(userRoleSet));
        Assert.assertEquals(listOfOptionalModeratorUser.get(), userService.findAllUsersByRoles(moderatorRoleSet));
        Assert.assertEquals(listOfOptionalSuperAdminUser.get(), userService.findAllUsersByRoles(superAdminRoleSet));
        Assert.assertEquals(listOfOptionalTrainerUser.get(), userService.findAllUsersByRoles(trainerRoleSet));
        Assert.assertEquals(listOfOptionalBasicUser.get(), userService.findAllUsersByRoles(basicRoleSet));
    }

    @Test
    public void when_enter_roles_then_throw_exception()
    {
        //given
        Optional<List<User>> emptyOptionalList = Optional.empty();
        //when
        Mockito.when(userRepository.findUsersByRolesIn(adminRoleSet)).thenReturn(emptyOptionalList);
        //then
        Assert.assertThrows(UserNotFoundException.class, ()-> userService.findAllUsersByRoles(adminRoleSet));
        Assert.assertThrows(UserNotFoundException.class, ()-> userService.findAllUsersByRoles(trainerRoleSet));

    }

    @Test
    public void when_enter_email_then_return_true()
    {
        //when
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        //then
        Assert.assertEquals(true, userService.existsByEmail(user.getEmail()));
    }

    @Test
    public void when_enter_email_then_return_false()
    {
        //when
        Mockito.when(userRepository.existsByEmail(admin.getEmail())).thenReturn(false);

        //then
        Assert.assertEquals(false, userService.existsByEmail(admin.getEmail()));
        Assert.assertEquals(false, userService.existsByEmail(trainer.getEmail()));
    }

    @Test
    public void when_enter_confirmation_code_then_user_will_be_found()
    {
        //given
        Optional<User> adminOptional = Optional.of(admin);

        //when
        Mockito.when(userRepository.findUserByConfirmationCode(admin.getConfirmationCode())).thenReturn(adminOptional);

        //then
        Assert.assertEquals(admin, userService.findByConfirmationCode(admin.getConfirmationCode()));
    }

    @Test
    public void when_enter_confirmation_code_then_throw_exception()
    {
        Optional<User> emptyOptional = Optional.empty();

        //when
        Mockito.when(userRepository.findUserByConfirmationCode(admin.getConfirmationCode())).thenReturn(emptyOptional);

        //then
        Assert.assertThrows(UserNotFoundException.class, () -> userService.findByConfirmationCode(admin.getConfirmationCode()));
        Assert.assertThrows(UserNotFoundException.class, () -> userService.findByConfirmationCode(user.getConfirmationCode()));
    }

}