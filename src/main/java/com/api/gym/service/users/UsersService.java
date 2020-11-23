package com.api.gym.service.users;

import com.api.gym.exceptions.RoleNotFoundException;
import com.api.gym.enums.ERole;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangeActive;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.security.services.UserDetailsImpl;
import com.api.gym.service.repository.RoleRepositoryService;
import com.api.gym.service.repository.UserRepositoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersService
{
    private final UserRepositoryService userRepositoryService;
    private final RoleRepositoryService roleRepositoryService;

    UsersService(UserRepositoryService userRepositoryService, RoleRepositoryService roleRepositoryService)
    {
        this.userRepositoryService = userRepositoryService;
        this.roleRepositoryService = roleRepositoryService;
    }

    public void changeUserProfile(SignupRequest signupRequest)
    {
        User user = userRepositoryService.findUserByEmail(signupRequest.getEmail());
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
        userRepositoryService.saveUser(user);
    }

    public UserDetailsImpl userDetails()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    public ResponseEntity<?> changeActive(ChangeActive changeActive)
    {
        User user = userRepositoryService.findUserByEmail(changeActive.getEmail());
        user.setActive(changeActive.getActive());
        userRepositoryService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("Active changed"));
    }

    public ResponseEntity<?> changeRoleByEmail(String email, ERole role, UserDetailsImpl userDetails, ERole currentUserRole)
    {
        User user = userRepositoryService.findUserByEmail(email);
        user.setRoles(findRole(role));
        if(currentUserRole.equals(ERole.ROLE_ADMIN))
        {
            user.setIdAdmin(userDetails.getId());
        }
        else
        {
            user.setIdTrainer(userDetails.getId());
        }
        userRepositoryService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("Successfully!"));
    }

    public Set<Role> findRole(ERole role)
    {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepositoryService.findRoleByName(role)
                .orElseThrow(() -> new RoleNotFoundException(role.toString()));
        roles.add(userRole);
        return roles;
    }

    public List<ShowUserResponse> findUsersByRole(ERole roles)
    {
        List<User> users = userRepositoryService.findAllUsersByRoles(findRole(roles));
        List<ShowUserResponse> showAllTrainersResponses = new ArrayList<>();
        for (User user : users)
            showAllTrainersResponses.add(new ShowUserResponse(user.getUserName(), user.getLastName(), user.getEmail(), user.getPhoneNumber()));
        return showAllTrainersResponses;
    }

}
