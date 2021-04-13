package com.api.gym;

import com.api.gym.enums.EGender;
import com.api.gym.enums.ERole;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import lombok.Getter;
import lombok.Setter;
import org.junit.Before;

import java.util.*;

@Getter
@Setter
public class TestDataPrepare
{
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




    public Set<Role> allRoleSet()
    {
        Set<Role> allRoles = new HashSet<>();
        allRoles.addAll(adminRoleSet);
        allRoles.addAll(trainerRoleSet);
        allRoles.addAll(moderatorRoleSet);
        allRoles.addAll(superAdminRoleSet);
        allRoles.addAll(userRoleSet);
        allRoles.addAll(basicRoleSet);
        return allRoles;
    }

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
        user.setConfirmEmail(true);
        user.setConfirmationCode(uuid.toString());
        user.setUserName(name);
        user.setLastName(name);
        user.setGender(EGender.MALE);
        user.setActive(true);
        user.setPhoneNumber("123456789");
        user.setRoles(createRoleSet(eRole));
        user.setEmail(name + "@" + name + ".pl");
        user.setId(null);
        return user;
    }
}
