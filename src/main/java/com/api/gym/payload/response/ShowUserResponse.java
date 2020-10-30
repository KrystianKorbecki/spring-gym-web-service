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
    String email;
    String phoneNumber;
}
