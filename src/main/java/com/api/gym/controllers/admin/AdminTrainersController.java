package com.api.gym.controllers.admin;

import com.api.gym.models.ERole;
import com.api.gym.models.Schedule;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangeActive;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.repository.ScheduleRepository;
import com.api.gym.service.ScheduleRepositoryService;
import com.api.gym.service.UserRepositoryService;
import com.api.gym.service.UsersService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminTrainersController
{
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ScheduleRequest
    {
        private List<Timestamp> start_date;
        private List<Timestamp> end_date;
        private String emailTrainer;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ScheduleUpdateRequest
    {
        private List<Timestamp> old_start_date;
        private List<Timestamp> old_end_date;
        private List<Timestamp> new_start_date;
        private List<Timestamp> new_end_date;
        private String emailTrainer;
    }

    private final UsersService usersService;
    private final UserRepositoryService userRepositoryService;
    private final ScheduleRepositoryService scheduleRepositoryService;


    AdminTrainersController(UsersService usersService, UserRepositoryService userRepositoryService, ScheduleRepositoryService scheduleRepositoryService)
    {
        this.usersService = usersService;
        this.userRepositoryService = userRepositoryService;
        this.scheduleRepositoryService = scheduleRepositoryService;
    }

    @GetMapping("/trainer")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showTrainers(@RequestParam(value = "email", required = false) String email)
    {
        if(email == null)
        {
            List<ShowUserResponse> showTrainers = new ArrayList<>(usersService.findUsersByRole(ERole.ROLE_TRAINER));
            return ResponseEntity.ok(showTrainers);
        }
        else
        {
            User user = userRepositoryService.findUserByEmail(email);
            return ResponseEntity.ok(user);
        }
    }

    @ApiOperation(value = "Change active for trainer")
    @PatchMapping("/trainer/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setActiveValue(@Valid @RequestBody ChangeActive changeActive)
    {
        return usersService.changeActive(changeActive);
    }

    @ApiOperation(value = "Create schedule for user")
    @PostMapping("/trainer/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest)
    {
        Schedule schedule = new Schedule();
        schedule.setStartDate(scheduleRequest.getStart_date());
        schedule.setEndDate(scheduleRequest.getEnd_date());
        schedule.setUser(userRepositoryService.findUserByEmail(scheduleRequest.getEmailTrainer()));
        scheduleRepositoryService.saveSchedule(schedule);
        return ResponseEntity.ok(new MessageResponse("Success"));
    }

    @GetMapping("/trainer/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showSchedule(@RequestParam(value = "email", required = true) String email)
    {
        List<Schedule> scheduleList = scheduleRepositoryService.findScheduleByUser(userRepositoryService.findUserByEmail(email));
        return ResponseEntity.ok(scheduleList);
    }

    @PatchMapping("/trainer/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateSchedule(@Valid @RequestBody ScheduleUpdateRequest scheduleUpdateRequest)
    {
        Schedule schedule = scheduleRepositoryService
                .findScheduleByStartDateAndEndDateAndUser(scheduleUpdateRequest.getOld_start_date(), scheduleUpdateRequest.getOld_end_date(),
                        userRepositoryService.findUserByEmail(scheduleUpdateRequest.getEmailTrainer()));
        schedule.setStartDate(scheduleUpdateRequest.getNew_start_date());
        schedule.setEndDate(scheduleUpdateRequest.getNew_end_date());
        scheduleRepositoryService.saveSchedule(schedule);
        return ResponseEntity.ok(schedule);
    }
}
