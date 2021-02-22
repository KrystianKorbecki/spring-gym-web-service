package com.api.gym.repository;

import com.api.gym.models.Message;
import com.api.gym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>
{
    @Override
    <S extends Message> S save(S entity);

    Optional<List<Message>> findAllBySenderAndRecipient(User sender, User recipient);

}
