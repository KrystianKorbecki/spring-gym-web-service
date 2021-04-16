package com.api.gym.repository.implementation;

import com.api.gym.exception.DatabaseException;
import com.api.gym.exception.UserTicketNotFoundException;
import com.api.gym.models.UserTicket;
import com.api.gym.repository.UserTicketRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UserTicketService
{
    private final UserTicketRepository userTicketRepository;

    public UserTicketService(UserTicketRepository userTicketRepository)
    {
        this.userTicketRepository = userTicketRepository;
    }

    public Integer countAllByPurchaseDateBetween(Timestamp startDate, Timestamp endDate)
    {
        try
        {
            return userTicketRepository.countAllByPurchaseDateBetween(startDate, endDate);
        }
        catch (Exception e)
        {
            throw new DatabaseException();
        }
    }

    public List<UserTicket> findAllByPurchaseDateBetween(Timestamp startDate, Timestamp endDate)
    {
        return userTicketRepository.findAllByPurchaseDateBetween(startDate, endDate).orElseThrow(()->new UserTicketNotFoundException(startDate, endDate));
    }
}
