package com.api.gym.converters;

import com.api.gym.enums.ERole;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.payload.response.ShowUserResponse;
import org.springframework.stereotype.Service;

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
            showUserResponseCollection.add(convertUserToShowUserResponse(user));
        }
        return showUserResponseCollection;
    }

    public List<ShowUserResponse> convertUserListToShowUserResponseList(List<User> users)
    {
        List<ShowUserResponse> showUserResponses = new ArrayList<>();
        for(User user:users)
        {
            showUserResponses.add(convertUserToShowUserResponse(user));
        }
        return showUserResponses;
    }

    public Set<ERole> convertRoleToRolesSet(ERole role)
    {
        Set<ERole> roles = new HashSet<>();
        roles.add(role);
        return roles;
    }

    public ShowUserResponse convertUserToShowUserResponse(User user)
    {
        ShowUserResponse showUserResponse = new ShowUserResponse();
        showUserResponse.setUserName(user.getUserName());
        showUserResponse.setPhoneNumber(user.getPhoneNumber());
        showUserResponse.setLastName(user.getLastName());
        showUserResponse.setProfileName(user.getProfileName());
        showUserResponse.setActive(user.getActive());
        return showUserResponse;
    }


}
