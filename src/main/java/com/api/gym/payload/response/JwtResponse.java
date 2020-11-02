package com.api.gym.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse
{
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String lastName;
	private String email;
	private String phoneNumber;
	private Date createDate;
	private List<String> roles;


	public JwtResponse(String accessToken, Long id, String username, String lastName,String email,String phoneNumber, Date createDate, List<String> roles)
	{
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.createDate = createDate;
	}
}
