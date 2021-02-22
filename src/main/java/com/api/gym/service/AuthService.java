package com.api.gym.service;

import com.api.gym.enums.ERole;
import com.api.gym.mail.EmailService;
import com.api.gym.models.Role;
import com.api.gym.models.User;
import com.api.gym.payload.request.LoginRequest;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.payload.response.JwtResponse;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.security.jwt.JwtUtils;
import com.api.gym.security.services.UserDetailsImpl;
import com.api.gym.repository.implementation.RoleService;
import com.api.gym.repository.implementation.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService
{
    private final GenerateCode generateCode;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;

    AuthService(GenerateCode generateCode, AuthenticationManager authenticationManager, JwtUtils jwtUtils, PasswordEncoder passwordEncoder,
                UserService userService, RoleService roleService, EmailService emailService)
    {
        this.generateCode = generateCode;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.roleService = roleService;
        this.emailService = emailService;
    }

    public ResponseEntity<?> authenticateUser(LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User authUser= userService.findUserByEmail(userDetails.getEmail());
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

    @Transactional
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest)
    {
        if (checkIfUserExists(signUpRequest.getEmail()))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        else
        {
            boolean exc = false;
            try
            {
                userService.saveUser(convertSignUpRequestToUser(signUpRequest));
            }
            catch (Exception e)
            {
                exc = true;
            }

            if(exc == false)
            {
                return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
            }
            else
            {
                return (ResponseEntity<?>) ResponseEntity.status(500);
            }

        }
    }
    public boolean checkIfUserExists(String email)
    {
        return userService.existsByEmail(email);
    }

    public User convertSignUpRequestToUser(SignupRequest signUpRequest)
    {
        User user = new User();
        String code = generateCode.generateCode();
        user.setLastName(signUpRequest.getLastName());
        user.setUserName(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setConfirmationCode(code);
        user.setChatCode(generateCode.generateCode());
        user.setProfileName(createUserProfileName(signUpRequest.getUsername(), signUpRequest.getLastName()));
        user.setRoles(setRolesForUser(signUpRequest));
        user.setBirthdayDate(signUpRequest.getBirthdayDate());
        user.setGender(signUpRequest.getGender());
        if(!signUpRequest.getConfirmEmail())
        {
            emailService.sendConfirmEmail(signUpRequest.getEmail(), code);
            user.setConfirmEmail(false);
        }
        else
        {
            user.setConfirmEmail(true);
        }
        return user;
    }

    public String createUserProfileName(String firstName, String lastName)
    {
        String profileName = firstName.toLowerCase() + "." + lastName.toLowerCase();
        int number = 1;
        while (userService.existsByProfileName(profileName + number))
        {
            number++;
        }
        return profileName + number;
    }

    public Set<Role> setRolesForUser(SignupRequest signUpRequest)
    {
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null)
        {
            roles.add(roleService.findRoleByName(ERole.ROLE_BASIC));
        }
        else
        {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" -> {
                        roles.add(roleService.findRoleByName(ERole.ROLE_ADMIN));
                    }
                    case "trainer" -> {
                        roles.add(roleService.findRoleByName(ERole.ROLE_TRAINER));
                    }
                    case "user" -> {
                        roles.add(roleService.findRoleByName(ERole.ROLE_USER));
                    }
                    default -> {
                        roles.add(roleService.findRoleByName(ERole.ROLE_BASIC));
                    }
                }
            });
        }
        return roles;
    }
}
