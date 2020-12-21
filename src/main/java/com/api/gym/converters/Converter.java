package com.api.gym.converters;

import com.api.gym.enums.ERole;
import com.api.gym.models.Role;
import com.api.gym.models.TrainingPlan;
import com.api.gym.models.User;
import com.api.gym.payload.request.TrainingPlanCreateRequest;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.service.repository.RoleService;
import com.api.gym.service.repository.UserService;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class Converter
{
    public Set<Role> convertRoleToRolesSet(Role role)
    {
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }

    public Collection<? extends ShowUserResponse> convertUserListToShowUserResponseCollection(List<User> userList)
    {
        Collection<ShowUserResponse> showUserResponseCollection = new HashSet<>();
        for(User user:userList)
        {
            ShowUserResponse showUserResponse = new ShowUserResponse(user.getUserName(), user.getLastName(), user.getEmail(), user.getPhoneNumber());
            showUserResponseCollection.add(showUserResponse);
        }
        return showUserResponseCollection;
    }

    public List<ShowUserResponse> convertUserListToShowUserResponseList(List<User> users)
    {
        List<ShowUserResponse> showUserResponses = new ArrayList<>();
        for(User user:users)
        {
            ShowUserResponse userResponse = new ShowUserResponse();
            userResponse.setEmail(user.getEmail());
            userResponse.setLastName(user.getLastName());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponse.setUserName(user.getUserName());
            showUserResponses.add(userResponse);
        }
        return showUserResponses;
    }

    public ShowUserResponse convertUserToShowUserResponse(User user)
    {
        ShowUserResponse showUserResponse = new ShowUserResponse();
        showUserResponse.setUserName(user.getUserName());
        showUserResponse.setPhoneNumber(user.getPhoneNumber());
        showUserResponse.setLastName(user.getLastName());
        showUserResponse.setEmail(user.getEmail());
        return showUserResponse;
    }


}
