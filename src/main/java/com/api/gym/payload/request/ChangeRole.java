package com.api.gym.payload.request;

import com.api.gym.enums.ERole;
import com.api.gym.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRole
{
    Set<ERole> addRoles;
    Set<ERole> deleteRoles;
}
