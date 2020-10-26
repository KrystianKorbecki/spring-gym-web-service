package com.api.gym.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig
{
    @Bean
    public Docket get()
    {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.api.gym.controllers"))
                .build().apiInfo(createApiInfo());
    }
    private ApiInfo createApiInfo() {
        return new ApiInfo("Gym Api",
                "Application for gym",
                "1.00",
                "",
                new Contact("Krystian", "", "krystian.korbecki36@interia.pl"),
                "License",
                "",
                Collections.emptyList()
        );
    }
}
