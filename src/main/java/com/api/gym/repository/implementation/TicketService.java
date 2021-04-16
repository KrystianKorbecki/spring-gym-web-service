package com.api.gym.repository.implementation;

import com.api.gym.exception.TicketNotFoundException;
import com.api.gym.models.Ticket;
import com.api.gym.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService
{
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository)
    {
        this.ticketRepository = ticketRepository;
    }

    public Ticket findById(Long id)
    {
        return ticketRepository.findById(id).orElseThrow(()->new TicketNotFoundException(id));
    }
}
