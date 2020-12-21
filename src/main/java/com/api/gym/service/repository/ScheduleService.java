package com.api.gym.service.repository;

import com.api.gym.exception.ScheduleNotFoundException;
import com.api.gym.models.Schedule;
import com.api.gym.models.User;
import com.api.gym.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService
{
    private final ScheduleRepository scheduleRepository;

    ScheduleService(ScheduleRepository scheduleRepository)
    {
        this.scheduleRepository = scheduleRepository;
    }

    public <S extends Schedule> void saveSchedule(S entity)
    {
        scheduleRepository.save(entity);
    }

    public Boolean existsScheduleByStartDateAndEndDateAndUser(LocalDateTime startDate, LocalDateTime endDate, User user)
    {
        return scheduleRepository.existsScheduleByStartDateAndEndDateAndUser(startDate, endDate, user);
    }

    public List<Schedule> findScheduleByUser(User user)
    {
        return scheduleRepository.findAllByUser(user).orElseThrow(() -> new ScheduleNotFoundException(user.getEmail()));
    }

    public Schedule findScheduleByStartDateAndEndDateAndUser(LocalDateTime startDate, LocalDateTime endDate, User user)
    {
        return scheduleRepository.findScheduleByStartDateAndEndDateAndUser(startDate, endDate, user)
                .orElseThrow(() -> new ScheduleNotFoundException(startDate, endDate, user.getEmail()));
    }

    public void saveScheduleList(List<LocalDateTime> startDate, List<LocalDateTime> endDate, User user)
    {
        for(int i = 0; i < startDate.size(); i++)
        {
            if(!existsScheduleByStartDateAndEndDateAndUser(startDate.get(i), endDate.get(i), user))
            {
                Schedule schedule = new Schedule();
                schedule.setUser(user);
                schedule.setStartDate(startDate.get(i));
                schedule.setEndDate(endDate.get(i));
                saveSchedule(schedule);
            }
        }
    }

}
