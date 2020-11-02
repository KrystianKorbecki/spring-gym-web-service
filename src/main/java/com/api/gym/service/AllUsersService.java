package com.api.gym.service;

import com.api.gym.exceptions.RoleNotFoundException;
import com.api.gym.models.ERole;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.repository.RoleRepository;
import com.api.gym.repository.UserRepository;
import com.api.gym.security.services.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AllUsersService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    AllUsersService(UserRepository userRepository, RoleRepository roleRepository)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDetailsImpl userDetails()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       return (UserDetailsImpl) authentication.getPrincipal();
    }

    public ResponseEntity<?> changeRoleByEmail(String email, ERole role, UserDetailsImpl userDetails)
    {
        User user = userRepository.findUserByEmail(email);
        user.setRoles(findRole(role));
        user.setIdAdmin(userDetails.getId());
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("Successfully!"));
    }

    public Set<Role> findRole(ERole role)
    {
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new RoleNotFoundException(role.toString()));
        roles.add(userRole);
        return roles;
    }

    public List<ShowUserResponse> findUsersByRole(ERole roles)
    {
        List<User> users = userRepository.findAllByRolesIn(findRole(roles));

        List<ShowUserResponse> showAllTrainersResponses = new ArrayList<>();
        for(User user : users)
            showAllTrainersResponses.add(new ShowUserResponse(user.getUserName(), user.getLastName(), user.getEmail(), user.getPhoneNumber()));
        return showAllTrainersResponses;
    }

    public User findUserByEmail(String email)
    {
        return userRepository.findUserByEmail(email);
    }
}
