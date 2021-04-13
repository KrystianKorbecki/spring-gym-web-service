package com.api.gym.message.service;

import com.api.gym.enums.ERole;
import com.api.gym.message.response.ShowAllUsers;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.repository.implementation.RoleService;
import com.api.gym.repository.implementation.UserService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MessagesService
{
    private final UserService userService;
    private final RoleService roleService;

    MessagesService(UserService userService, RoleService roleService)
    {
        this.userService = userService;
        this.roleService = roleService;
    }

    public Set<ShowAllUsers> showChatUsers(String email)
    {
        Set<ShowAllUsers> showAllUsersSet = new HashSet<>();
        Set<Role> roles = userService.findUserByEmail(email).getRoles();
        Role adminRole = roleService.findRoleByName(ERole.ROLE_ADMIN);
        Role trainerRole = roleService.findRoleByName(ERole.ROLE_TRAINER);
        Role userRole = roleService.findRoleByName(ERole.ROLE_USER);
        if(roles.contains(adminRole))
        {
            showAllUsersSet.addAll(showChatUsersForAdmin(adminRole, trainerRole));
        }
        if(roles.contains(trainerRole))
        {
            showAllUsersSet.addAll(showChatUsersForTrainer(adminRole, trainerRole, userRole));
        }
        if(roles.contains(userRole))
        {
            showAllUsersSet.addAll(showChatUsersForUser());
        }
        return showAllUsersSet;
    }

    public Set<ShowAllUsers> showChatUsersForAdmin(Role roleAdmin, Role roleTrainer)
    {
        Set<ShowAllUsers> showAllUsers = convertUserToShowAllUsers(userService.findUsersByRole(roleAdmin));
        showAllUsers.addAll(convertUserToShowAllUsers(userService.findUsersByRole(roleTrainer)));
        return showAllUsers;
    }

    public Set<ShowAllUsers> showChatUsersForTrainer(Role roleAdmin, Role roleTrainer, Role roleUser)
    {
        Set<ShowAllUsers> showAllUsers = convertUserToShowAllUsers(userService.findUsersByRole(roleAdmin));
        showAllUsers.addAll(convertUserToShowAllUsers(userService.findUsersByRole(roleTrainer)));
        showAllUsers.addAll(convertUserToShowAllUsers(userService.findUsersByRole(roleUser)));
        return showAllUsers;
    }

    public Set<ShowAllUsers> showChatUsersForUser()
    {
        return convertUserToShowAllUsers(userService.findUsersByRole(roleService.findRoleByName(ERole.ROLE_TRAINER)));
    }

    public Set<ShowAllUsers> convertUserToShowAllUsers(List<User> users)
    {
        Set<ShowAllUsers> showAllUsersList = new HashSet<>();
        for(User user:users)
        {
            ShowAllUsers showAllUsers = new ShowAllUsers();
            showAllUsers.setName(user.getUserName());
            showAllUsers.setLastName(user.getLastName());
            showAllUsers.setRole(user.getRoles().toString());
            showAllUsersList.add(showAllUsers);
        }
        return showAllUsersList;
    }
}
