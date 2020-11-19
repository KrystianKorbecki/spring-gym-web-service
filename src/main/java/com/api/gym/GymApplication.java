package com.api.gym;


import com.api.gym.payload.request.SignupRequest;
import com.api.gym.service.AuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableJpaRepositories("com.api.gym.repository")
public class GymApplication
{
    private static AuthService authService;
    GymApplication(AuthService authService)
    {
        this.authService = authService;
    }

    @PostConstruct
    public static void initUser()
    {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("admin@admin.pl");
        signupRequest.setLastName("Admin");
        signupRequest.setUsername("Admin");
        signupRequest.setPassword("password");
        signupRequest.setRole(roles);
        signupRequest.setPhoneNumber("234567821");
        authService.registerUser(signupRequest);
    }

    public static void main(String[] args)
    {
        SpringApplication.run(GymApplication.class, args);
    }





}
