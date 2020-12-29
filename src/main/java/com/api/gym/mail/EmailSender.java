package com.api.gym.mail;

public interface EmailSender
{
    void sendEmail(String to, String subject, String content);

}
