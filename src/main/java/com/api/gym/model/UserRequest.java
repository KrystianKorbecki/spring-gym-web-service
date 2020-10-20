package com.api.gym.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest
{
    private String name;

    private String lastName;

    private int phoneNumber;

    private String emailAddress;

    private String password;

    private String role;

}
