package com.api.gym.controllers;

import javax.validation.Valid;

import com.api.gym.payload.request.LoginRequest;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController
{
	AuthService authService;

	public AuthController(AuthService authService)
	{
		this.authService = authService;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
	{
		return authService.authenticateUser(loginRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
	{
		return authService.registerUser(signUpRequest);
	}
}
