package com.api.gym.security.services;

import com.api.gym.models.User;
import com.api.gym.repository.UserRepository;
import com.api.gym.service.repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	UserService userService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email)
	{
		User user = userService.findUserByEmail(email);

		return UserDetailsImpl.build(user);
	}

}
