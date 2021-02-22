package com.api.gym.message.controller;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class WebSocketMessage
{
  private String senderToken;
  private String recipientChatCode;
  private String message;
  private Timestamp date;
  
  public WebSocketMessage(String senderToken, String recipientChatCode, String message, Timestamp date)
  {
    this.senderToken  = senderToken;
    this.recipientChatCode = recipientChatCode;
    this.message = message;
    this.date = date;
  }
}
