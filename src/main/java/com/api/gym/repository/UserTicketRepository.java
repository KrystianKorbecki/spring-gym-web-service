package com.api.gym.repository;

import com.api.gym.models.UserTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserTicketRepository extends JpaRepository<UserTicket, Long>
{
    Integer countAllByPurchaseDateBetween(Timestamp startDate, Timestamp endDate);
    Optional<List<UserTicket>> findAllByPurchaseDateBetween(Timestamp startDate, Timestamp endDate);
}
