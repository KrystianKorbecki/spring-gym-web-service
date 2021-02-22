package com.api.gym.service.users;

import com.api.gym.enums.ERole;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangeRole;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.security.services.UserDetailsImpl;
import com.api.gym.converters.Converter;
import com.api.gym.repository.implementation.RoleService;
import com.api.gym.repository.implementation.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsersService
{
    private final UserService userService;
    private final RoleService roleService;
    private final Converter converter;

    UsersService(UserService userService, RoleService roleService, Converter converter)
    {
        this.userService = userService;
        this.roleService = roleService;
        this.converter = converter;
    }

    public void changeUserProfile(SignupRequest signupRequest)
    {
        User user = userService.findUserByEmail(signupRequest.getEmail());
        if(signupRequest.getBirthdayDate() != null)
        {
            user.setBirthdayDate(signupRequest.getBirthdayDate());
        }
        if(signupRequest.getGender() != null)
        {
            user.setGender(signupRequest.getGender());
        }
        if(signupRequest.getLastName() != null)
        {
            user.setLastName(signupRequest.getLastName());
        }
        if(signupRequest.getPhoneNumber() != null)
        {
            user.setPhoneNumber(signupRequest.getPhoneNumber());
        }
        if(signupRequest.getUsername() != null)
        {
            user.setUserName(signupRequest.getUsername());
        }
        userService.saveUser(user);
    }

    public UserDetailsImpl userDetails()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    public ResponseEntity<?> changeActive(String profileName)
    {
        User user = userService.findUserByProfileName(profileName);
        user.setActive(!user.getActive());
        userService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("Active changed"));
    }

    public ResponseEntity<?> changeRoleByProfileName(String profileName, ChangeRole changeRole, UserDetailsImpl userDetails)
    {
        boolean exception = false;
        try
        {
            User user = userService.findUserByProfileName(profileName);
            if(changeRole.getAddRoles().equals(ERole.ROLE_ADMIN))
            {
                user.setRoles(appendRoles(user.getRoles(), changeRole.getAddRoles()));
                user.setRoles(dropRoleFromSet(user.getRoles(), changeRole.getDeleteRoles()));
            }
            else if (changeRole.getAddRoles().equals(ERole.ROLE_TRAINER) || changeRole.getAddRoles().equals(ERole.ROLE_MODERATOR))
            {
                user.setRoles(appendRoles(user.getRoles(), changeRole.getAddRoles()));
                user.setIdAdmin(userDetails.getId());
            }
            else
            {
                user.setRoles(appendRoles(user.getRoles(), changeRole.getAddRoles()));
                user.setIdTrainer(userDetails.getId());
            }
        }
        catch (Exception e)
        {
            exception = true;
        }

        if(exception == false)
        {
            return (ResponseEntity<?>) ResponseEntity.accepted();
        }
        else
        {
            return (ResponseEntity<?>) ResponseEntity.status(500);
        }

    }

    public Set<Role> appendRoles(Set<Role> roleSet, Set<ERole> addRoles)
    {
        Set<Role> newRoleSet = new HashSet<>();
        newRoleSet.addAll(roleSet);
        for(ERole role:addRoles)
        {
            newRoleSet.add(roleService.findRoleByName(role));
        }
        return newRoleSet;
    }

    public Set<Role> dropRoleFromSet(Set<Role> roleSet, Set<ERole> roleToDrop)
    {
        for(ERole eRole:roleToDrop)
        {
            Role role = roleService.findRoleByName(eRole);
            roleSet.remove(role);
        }
        return roleSet;
    }

    public Set<Role> dropRoleFromSet(Set<Role> roleSet, ERole roleToDrop)
    {
        roleSet.remove(roleService.findRoleByName(roleToDrop));
        return roleSet;
    }



}
