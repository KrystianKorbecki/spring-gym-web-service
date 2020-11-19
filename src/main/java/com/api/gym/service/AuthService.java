package com.api.gym.service;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService
{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final GenerateUserCode generateUserCode;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UsersService usersService;

    AuthService(UserRepository userRepository, RoleRepository roleRepository, GenerateUserCode generateUserCode,
                AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, UsersService usersService)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.generateUserCode = generateUserCode;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.usersService = usersService;
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User authUser= userRepository.findUserByEmail(userDetails.getEmail());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                authUser.getLastName(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                authUser.getPhoneNumber(),
                authUser.getCreateDate(),
                roles));
    }

    public ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest)
    {
        if (checkIfUserExists(signUpRequest.getEmail()))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        else
        {
            userRepository.save(convertSignUpRequestToUser(signUpRequest));
            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        }
    }
    public boolean checkIfUserExists(String email)
    {
        return userRepository.existsByEmail(email);
    }

    public User convertSignUpRequestToUser(SignupRequest signUpRequest)
    {
        User user = new User();
        user.setLastName(signUpRequest.getLastName());
        user.setUserName(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setCode(generateUserCode.Generate(6));
        user.setRoles(setRolesForUser(signUpRequest));
        user.setBirthdayDate(signUpRequest.getBirthdayDate());
        user.setGender(signUpRequest.getGender());
        return user;
    }

    public Set<Role> setRolesForUser(SignupRequest signUpRequest)
    {
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null)
        {
            roles.addAll(usersService.findRole(ERole.ROLE_BASIC));
        }
        else
        {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        roles.addAll(usersService.findRole(ERole.ROLE_ADMIN));
                    }
                    case "trainer" -> {
                        roles.addAll(usersService.findRole(ERole.ROLE_TRAINER));
                    }
                    case "user" -> {
                        roles.addAll(usersService.findRole(ERole.ROLE_USER));
                    }
                    default -> {
                        roles.addAll(usersService.findRole(ERole.ROLE_BASIC));
                    }
                }
            });
        }
        return roles;
    }
}
