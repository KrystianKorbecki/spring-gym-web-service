package com.api.gym.service;

import com.api.gym.exceptions.RoleNotFoundException;
import com.api.gym.models.ERole;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.payload.request.LoginRequest;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.payload.response.JwtResponse;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.repository.RoleRepository;
import com.api.gym.repository.UserRepository;
import com.api.gym.security.jwt.JwtUtils;
import com.api.gym.security.services.UserDetailsImpl;
import com.api.gym.service.GenerateUserCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService
{
    UserRepository userRepository;
    RoleRepository roleRepository;
    GenerateUserCode generateUserCode;
    AuthenticationManager authenticationManager;
    JwtUtils jwtUtils;
    PasswordEncoder passwordEncoder;

    AuthService(UserRepository userRepository, RoleRepository roleRepository, GenerateUserCode generateUserCode,
                AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.generateUserCode = generateUserCode;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    public ResponseEntity<?> registerUser(SignupRequest signUpRequest)
    {
        if (userRepository.existsByEmail(signUpRequest.getEmail()))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        else
        {
            // Create new user's account
            Date currentDate = new Date();
            User user = new User();
            user.setLastName(signUpRequest.getLastName());
            user.setUserName(signUpRequest.getUsername());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            user.setCreateDate(new Timestamp(currentDate.getTime()));
            user.setPhoneNumber(signUpRequest.getPhoneNumber());
            user.setCode(generateUserCode.Generate(6));
            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null)
            {
                Role userRole = roleRepository.findByName(ERole.ROLE_BASIC)
                        .orElseThrow(() -> new RoleNotFoundException(ERole.ROLE_BASIC.toString()));
                roles.add(userRole);
            }
            else
            {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin" -> {
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RoleNotFoundException(ERole.ROLE_ADMIN.toString()));
                            roles.add(adminRole);
                        }
                        case "trainer" -> {
                            Role trainerRole = roleRepository.findByName(ERole.ROLE_TRAINER)
                                    .orElseThrow(() -> new RoleNotFoundException(ERole.ROLE_TRAINER.toString()));
                            roles.add(trainerRole);
                        }
                        case "user" -> {
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RoleNotFoundException(ERole.ROLE_USER.toString()));
                            roles.add(userRole);
                        }
                        default -> {
                            Role basicRole = roleRepository.findByName(ERole.ROLE_BASIC)
                                    .orElseThrow(() -> new RoleNotFoundException(ERole.ROLE_BASIC.toString()));
                            roles.add(basicRole);
                        }
                    }
                });
            }

            user.setRoles(roles);
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
    }
}
