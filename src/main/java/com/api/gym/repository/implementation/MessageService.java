package com.api.gym.repository.implementation;

import com.api.gym.exception.MessageNotFoundException;
import com.api.gym.models.Message;
import com.api.gym.models.User;
import com.api.gym.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService
{
    private final MessageRepository messageRepository;

    MessageService(MessageRepository messageRepository)
    {
        this.messageRepository = messageRepository;
    }

    public List<Message> findAllBySenderAndRecipient(User sender, User recipient)
    {
        return messageRepository.findAllBySenderAndRecipient(sender,recipient).orElseThrow(MessageNotFoundException::new);
    }

    public <S extends Message> S save(S entity)
    {
        return messageRepository.save(entity);
    }
}
