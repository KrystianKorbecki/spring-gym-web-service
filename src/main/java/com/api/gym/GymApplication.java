package com.api.gym;


import com.api.gym.controllers.AuthController;
import com.api.gym.payload.request.SignupRequest;
import com.api.gym.service.AuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.Link;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
        signupRequest.setConfirmEmail(false);
        Link link = linkTo(AuthController.class).withSelfRel();
        authService.registerUser(signupRequest,  link);
    }

    public static void main(String[] args)
    {
        SpringApplication.run(GymApplication.class, args);
    }

//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOrigins(Collections.singletonList("*"));
//        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }



}
