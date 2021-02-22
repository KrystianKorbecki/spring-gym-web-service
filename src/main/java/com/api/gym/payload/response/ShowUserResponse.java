package com.api.gym.payload.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ShowUserResponse
{
    String userName;
    String lastName;
    String profileName;
    String phoneNumber;
    boolean active;
}
