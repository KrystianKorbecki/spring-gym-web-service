package com.api.gym.service;

import com.api.gym.models.Schedule;
import com.api.gym.models.User;
import com.api.gym.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ScheduleRepositoryService
{
    private final ScheduleRepository scheduleRepository;

    ScheduleRepositoryService(ScheduleRepository scheduleRepository)
    {
        this.scheduleRepository = scheduleRepository;
    }

    public <S extends Schedule> void saveSchedule(S entity)
    {
        scheduleRepository.save(entity);
    }

    public List<Schedule> findScheduleByUser(User user)
    {
        return scheduleRepository.findAllByUser(user);
    }

    public Schedule findScheduleByStartDateAndEndDateAndUser(List<Timestamp> startDate, List<Timestamp> endDate, User user)
    {
        return scheduleRepository.findScheduleByStartDateAndEndDateAndUser(startDate, endDate, user);
    }

}
