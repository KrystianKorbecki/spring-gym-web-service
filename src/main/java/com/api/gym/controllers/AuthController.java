package com.api.gym.controllers;

import javax.swing.text.html.parser.Entity;
import javax.validation.Valid;

import com.api.gym.models.Message;
import com.api.gym.models.User;
import com.api.gym.payload.request.LoginRequest;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.payload.response.JwtResponse;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.repository.UserRepository;
import com.api.gym.security.services.UserDetailsImpl;
import com.api.gym.service.AuthService;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@CrossOrigin(originPatterns = "*",allowCredentials="true",allowedHeaders = "*")
public class AuthController
{
	AuthService authService;
	UserRepository userRepository;

	public AuthController(AuthService authService, UserRepository userRepository)
	{
		this.authService = authService;
		this.userRepository = userRepository;
	}

	/**
	 * This method is used to authorize users by providing email and password
	 * @param loginRequest include email and password
	 * @return {@link JwtResponse} with data necessary for logging in
	 */
	@PostMapping(name = "/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
	{
		Link link = linkTo(methodOn(AuthController.class).authenticateUser(loginRequest)).withSelfRel();
		JwtResponse jwtResponse = authService.authenticateUser(loginRequest);
		jwtResponse.add(link);
		return	new ResponseEntity<>(jwtResponse, HttpStatus.ACCEPTED);

	}

	/**
	 * This method is used to create new users by provide {@link SignupRequest}
	 * @param signUpRequest includes primary data about user, and login data
	 * @return {@link MessageResponse} with data about registration process
	 */
	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest)
	{
		Link link = linkTo(methodOn(AuthController.class).registerUser(signUpRequest)).withSelfRel();
		return authService.registerUser(signUpRequest, link);
	}
}
