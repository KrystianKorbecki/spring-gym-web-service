package com.api.gym.controllers.admin;

import com.api.gym.enums.ERole;
import com.api.gym.models.Schedule;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangeActive;
import com.api.gym.payload.request.ScheduleRequest;
import com.api.gym.payload.request.ScheduleUpdateRequest;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.converters.Converter;
import com.api.gym.service.repository.RoleService;
import com.api.gym.service.repository.ScheduleService;
import com.api.gym.service.repository.UserService;
import com.api.gym.service.users.UsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminTrainersController
{
    private final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private final UsersService usersService;
    private final UserService userService;
    private final ScheduleService scheduleService;
    private final Converter converter;
    private final RoleService roleService;

    AdminTrainersController(UsersService usersService, UserService userService, ScheduleService scheduleService, Converter converter, RoleService roleService)
    {
        this.usersService = usersService;
        this.userService = userService;
        this.scheduleService = scheduleService;
        this.converter = converter;
        this.roleService = roleService;
    }

    @GetMapping("/trainer")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public ResponseEntity<?> showTrainers(@RequestParam(value = "email", required = false) String email)
    {
        if(email == null)
        {
            List<ShowUserResponse> showTrainers = new ArrayList<>(converter.convertUserListToShowUserResponseCollection(userService.findUsersByRole(roleService.findRoleByName(ERole.ROLE_TRAINER))));
            return ResponseEntity.ok(showTrainers);
        }
        else
        {
            User user = userService.findUserByEmail(email);
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

    @ApiOperation(value = "Create schedule for user, you must use date format: " + DATE_FORMAT)
    @PostMapping("/trainer/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest)
    {
        if(scheduleRequest.getStartDate().size() == scheduleRequest.getEndDate().size())
        {
            scheduleService.saveScheduleList(scheduleRequest.getStartDate(), scheduleRequest.getEndDate(), userService.findUserByEmail(scheduleRequest.getEmailTrainer()));
            return ResponseEntity.ok(new MessageResponse("Success"));
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/trainer/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showSchedule(@RequestParam(value = "email", required = true) String email)
    {
        List<Schedule> scheduleList = scheduleService.findScheduleByUser(userService.findUserByEmail(email));
        return ResponseEntity.ok(scheduleList);
    }

    @PatchMapping("/trainer/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update schedule for user, you must use date format: " + DATE_FORMAT)
    public ResponseEntity<?> updateSchedule(@Valid @RequestBody ScheduleUpdateRequest scheduleUpdateRequest)
    {
        if(scheduleUpdateRequest.getOldStartDate().size() == scheduleUpdateRequest.getOldEndDate().size() && scheduleUpdateRequest.getNewEndDate().size() == scheduleUpdateRequest.getNewStartDate().size())
        {
            User user = userService.findUserByEmail(scheduleUpdateRequest.getEmailTrainer());
            for (int i = 0; i < scheduleUpdateRequest.getOldEndDate().size(); i++)
            {
                Schedule schedule = scheduleService.findScheduleByStartDateAndEndDateAndUser(scheduleUpdateRequest.getOldStartDate().get(i), scheduleUpdateRequest.getOldEndDate().get(i), user);
                schedule.setStartDate(scheduleUpdateRequest.getNewStartDate().get(i));
                schedule.setEndDate(scheduleUpdateRequest.getNewEndDate().get(i));
                scheduleService.saveSchedule(schedule);
            }
            return ResponseEntity.ok("Success");
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
