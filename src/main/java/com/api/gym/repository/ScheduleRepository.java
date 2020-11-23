package com.api.gym.repository;

import com.api.gym.models.Schedule;
import com.api.gym.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long>
{
    <S extends Schedule> S save(S entity);

    List<Schedule> findAllByUser(User user);

    Schedule findScheduleByStartDateInAndEndDateInAndUser(List<Timestamp> startDate, List<Timestamp> endDate, User user);
}
