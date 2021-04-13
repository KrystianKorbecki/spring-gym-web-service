//package com.api.gym.assembler;
//
//import com.api.gym.controllers.AuthController;
//import com.api.gym.models.User;
//import com.api.gym.payload.request.LoginRequest;
//import com.api.gym.payload.response.JwtResponse;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.RepresentationModelAssembler;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@Component
//public class JwtResponseAssembler implements RepresentationModelAssembler<JwtResponse, EntityModel<JwtResponse>>
//{
//
//
//
//    @Override
//    public EntityModel<JwtResponse> toModel(JwtResponse entity) {
//        EntityModel<JwtResponse> jwtModel = EntityModel.of(entity,
//                linkTo(AuthController.class).withSelfRel());
//
//        return jwtModel;
//    }
//}
