package com.api.gym.controllers;

import com.api.gym.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestController
{
	@Autowired
	JwtUtils jwtUtils;

	@GetMapping("/all")
	public String allAccess()
	{
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public String userAccess()
	{
		return "User Content.";
	}

	@GetMapping("/trainer")
	@PreAuthorize("hasRole('TRAINER')")
	public String moderatorAccess()
	{
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Resource<?>> adminAccess()
	{
		Link link = linkTo(TestController.class).withSelfRel();
		Resource<?> resource = new Resource<>("Admin", link);
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}
}
