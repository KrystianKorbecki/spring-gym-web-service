package com.api.gym.message.controller;

import com.api.gym.message.response.ShowAllUsers;
import com.api.gym.message.service.MessagesService;
import com.api.gym.models.User;
import com.api.gym.security.jwt.JwtUtils;
import com.api.gym.repository.implementation.UserService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class WebSocketController
{
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final JwtUtils jwtUtils;
  private final MessagesService messagesService;
  private final UserService userService;

  public WebSocketController(SimpMessagingTemplate simpMessagingTemplate, JwtUtils jwtUtils, MessagesService messagesService, UserService userService)
  {
    this.jwtUtils = jwtUtils;
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.messagesService = messagesService;
    this.userService = userService;
  }
  
  @MessageMapping("/register")
  @SendToUser("/queue/newMember")
  public Set<ShowAllUsers> registerUser(String userToken)
  {
    User user = userService.findUserByEmail(jwtUtils.getUserNameFromJwtToken(userToken));
    simpMessagingTemplate.convertAndSend("/topic/newMember", user.getChatCode());
    return messagesService.showChatUsers(user.getEmail());
  }
  
  @MessageMapping("/unregister")
  @SendTo("/topic/disconnectedUser")
  public String unregisterUser(String webChatUsername)
  {
    return "";
  }

  @MessageMapping("/message")
  public void greeting(WebSocketMessage message)
  {
    simpMessagingTemplate.convertAndSendToUser(message.getRecipientChatCode(), "/msg", message);
  }
}
