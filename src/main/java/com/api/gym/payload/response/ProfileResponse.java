package com.api.gym.payload.response;

import com.api.gym.models.Photo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse
{
    private String name;
    private String lastName;
    private List<Photo> photos;

}
