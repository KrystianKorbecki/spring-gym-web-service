package com.api.gym.config;

import com.api.gym.filter.JwtFilter;
import com.api.gym.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private CustomUserDetailsService customUserDetailsService;
    private JwtFilter jwtFilter;

    SecurityConfig(CustomUserDetailsService customUserDetailsService, JwtFilter jwtFilter)
    {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
    {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    protected AuthenticationManager authenticationManager() throws Exception
    {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
            http
                    .csrf().disable().authorizeRequests()
                    .antMatchers("/register").permitAll()
                    .antMatchers("/authenticate").permitAll()
                    .antMatchers("/login").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll().
                    anyRequest().authenticated()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
            http.cors().disable();
//            http.formLogin()
//                .loginPage("/login")
//                .usernameParameter("email")
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
    }
}
