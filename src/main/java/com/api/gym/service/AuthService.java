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
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
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

    /**
     * The method implements an authentication algorithm, if wrong data is provided, Exception is returned
     * @param loginRequest It's submitted form with email and password
     * @return {@link JwtResponse} This is the class that contains the required authentication data
     */
    public JwtResponse authenticateUser(LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User authUser= userService.findUserByEmail(userDetails.getEmail());

        return new JwtResponse(jwt, authUser.getUserName(), authUser.getLastName(), authUser.getProfileName(), authUser.getRoles(), authUser.getActive(), authUser.getConfirmEmail());
    }

    @Transactional
    public ResponseEntity<MessageResponse> registerUser(SignupRequest signUpRequest, Link link)
    {
        if (userService.existsByEmail(signUpRequest.getEmail()))
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!").add(link));
        }
        else
        {
            try{
                userService.saveUser(convertSignUpRequestToUser(signUpRequest));
            }catch (Exception e)
            {
                return new ResponseEntity<>(new MessageResponse("Internal error").add(link), HttpStatus.INTERNAL_SERVER_ERROR);
            }finally {
                return ResponseEntity.ok(new MessageResponse("User registered successfully!").add(link));
            }
        }
    }

    /**
     * The method convert {@link SignupRequest} to {@link User} class
     * @param signUpRequest This is the submitted registration form
     * @return {@link User} class after convert
     */
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
        user.setProfileName(createUserProfileName(signUpRequest.getUsername(), signUpRequest.getLastName()));
        user.setRoles(setRolesForUser(signUpRequest.getRole()));
        user.setBirthdayDate(signUpRequest.getBirthdayDate());
        user.setGender(signUpRequest.getGender());
        user.setActive(true);
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

    /**
     * This method creates a unique name for each user
     * @param firstName It's taken from {@link SignupRequest}
     * @param lastName It's taken from {@link SignupRequest}
     * @return String with linked word firstName + lastName + number(If there are several users with the same first and last names)
     */
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

    /**
     * This method assigns the user the appropriate {@link Role} entered in the form (The method is only for tests)
     * @param signUpRequestRole It's contains submitted Roles name by {@link SignupRequest} form
     * @return {@link Set<Role>} This is the Set with the Roles found in the database
     */
    public Set<Role> setRolesForUser(Set<String> signUpRequestRole)
    {
        Set<String> strRoles = signUpRequestRole;
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
