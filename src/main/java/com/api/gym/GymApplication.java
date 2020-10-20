package com.api.gym;

import com.api.gym.model.UserRequest;
import com.api.gym.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableJpaRepositories("com.api.gym.repository")
public class GymApplication
{
    @Autowired
    RegistrationService registrationService;

    public static void main(String[] args) {
        SpringApplication.run(GymApplication.class, args);
    }

    @PostConstruct
    public void initusers()
    {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmailAddress("admin@admin.pl");
        userRequest.setLastName("Admin");
        userRequest.setName("Admin");
        userRequest.setPassword("password");
        userRequest.setRole("ADMIN");
        registrationService.createUser(userRequest);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer()
    {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry)
            {
                registry.addMapping("/*").allowedHeaders("*").allowedOrigins("*").allowCredentials(true);
            }
        };
    }


}
