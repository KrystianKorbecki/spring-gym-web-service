//package com.api.gym.controllers;
//
//import com.api.gym.TestDataPrepare;
//import com.api.gym.payload.request.LoginRequest;
//import com.api.gym.payload.response.JwtResponse;
//import com.api.gym.service.AuthService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(AuthController.class)
//public class AuthControllerTest
//{
//
//    @Autowired
//    public MockMvc mockMvc;
//
//    @MockBean
//    private AuthService authService;
//
//    private final TestDataPrepare testDataPrepare = new TestDataPrepare();
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Test
//    public void when_enter_email_and_password_then_user_will_be_authenticate() throws Exception
//    {
//        //given
//        JwtResponse jwtResponse = new JwtResponse("token", "Admin", "Admin", "admin.admin", testDataPrepare.getAdminRoleSet(), true, true);
//        LoginRequest loginRequest = new LoginRequest("admin@admin.pl","password");
//
//        //when
//        when(authService.authenticateUser(loginRequest)).thenReturn(jwtResponse);
//
//        //then
//        this.mockMvc.perform(post("/signnin").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(loginRequest)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(jwtResponse)));
//
//    }
//}
