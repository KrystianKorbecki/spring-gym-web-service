package com.api.gym.controllers;

import javax.validation.Valid;

import com.api.gym.payload.request.LoginRequest;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.repository.UserRepository;
import com.api.gym.security.services.UserDetailsImpl;
import com.api.gym.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuthController
{
	AuthService authService;
	UserRepository userRepository;

	public AuthController(AuthService authService, UserRepository userRepository)
	{
		this.authService = authService;
		this.userRepository = userRepository;
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
