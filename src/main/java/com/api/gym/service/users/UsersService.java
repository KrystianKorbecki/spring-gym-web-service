package com.api.gym.service.users;

import com.api.gym.enums.ERole;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangeActive;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.security.services.UserDetailsImpl;
import com.api.gym.converters.Converter;
import com.api.gym.service.repository.RoleService;
import com.api.gym.service.repository.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<?> changeActive(ChangeActive changeActive)
    {
        User user = userService.findUserByEmail(changeActive.getEmail());
        user.setActive(changeActive.getActive());
        userService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("Active changed"));
    }

    public ResponseEntity<?> changeRoleByEmail(String email, ERole role, UserDetailsImpl userDetails, ERole currentUserRole)
    {
        User user = userService.findUserByEmail(email);
        user.setRoles(converter.convertRoleToRolesSet(roleService.findRoleByName(role)));
        if(currentUserRole.equals(ERole.ROLE_ADMIN))
        {
            user.setIdAdmin(userDetails.getId());
        }
        else
        {
            user.setIdTrainer(userDetails.getId());
        }
        userService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("Successfully!"));
    }



}
