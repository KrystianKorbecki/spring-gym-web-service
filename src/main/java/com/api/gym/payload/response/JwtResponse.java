package com.api.gym.payload.response;

import com.api.gym.enums.ERole;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse extends RepresentationModel<JwtResponse>
{
	private String token;
	private String type = "Bearer";
	private String username;
	private String lastName;
	private String profileName;
	private Set <Role> roles;
	private Boolean active;
	private Boolean emailConfirmed;


	public JwtResponse(String accessToken, String username, String lastName, String profileName, Set<Role> roles, Boolean active, Boolean emailConfirmed)
	{
		this.token = accessToken;
		this.username = username;
		this.roles = roles;
		this.lastName = lastName;
		this.profileName = profileName;
		this.active = active;
		this.emailConfirmed = emailConfirmed;
	}
}
