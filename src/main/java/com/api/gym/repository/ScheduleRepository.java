package com.api.gym.repository;

import com.api.gym.models.Schedule;
import com.api.gym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>
{
    <S extends Schedule> S save(S entity);

    Optional<List<Schedule>> findAllByUser(User user);

    Optional<Schedule> findScheduleByStartDateAndEndDateAndUser(LocalDateTime startDate, LocalDateTime endDate, User user);

    Boolean existsScheduleByStartDateAndEndDateAndUser(LocalDateTime startDate, LocalDateTime endDate, User user);
}
