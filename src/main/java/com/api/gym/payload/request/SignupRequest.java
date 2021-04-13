package com.api.gym.payload.request;

import com.api.gym.enums.EGender;
import com.api.gym.enums.ERole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest
{
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 3, max = 20)
    private String lastName;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    private Date birthdayDate;

    @NotBlank
    private EGender gender;

    @NotBlank
    @Size(max = 15)
    private String phoneNumber;

    @JsonIgnore
    private Set<String> role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    private Boolean confirmEmail;

}
