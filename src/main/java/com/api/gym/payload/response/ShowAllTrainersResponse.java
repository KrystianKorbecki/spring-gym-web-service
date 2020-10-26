package com.api.gym.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowAllTrainersResponse
{
    String userName;
    String lastName;
    String email;
}
