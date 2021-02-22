package com.api.gym.message.response;

import com.api.gym.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowAllUsers
{
    String name;
    String lastName;
    String chatCode;
    String role;
}
