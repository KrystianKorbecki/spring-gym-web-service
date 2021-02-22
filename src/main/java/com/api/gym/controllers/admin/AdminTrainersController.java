package com.api.gym.controllers.admin;

import com.api.gym.enums.ERole;
import com.api.gym.models.Schedule;
import com.api.gym.models.User;
import com.api.gym.payload.request.ScheduleRequest;
import com.api.gym.payload.request.ScheduleUpdateRequest;
import com.api.gym.payload.response.MessageResponse;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.converters.Converter;
import com.api.gym.repository.implementation.RoleService;
import com.api.gym.repository.implementation.ScheduleService;
import com.api.gym.repository.implementation.UserService;
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

    @GetMapping("/trainers/{pageNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showBasicUser(@PathVariable int pageNumber, @RequestParam(required = false) int pageSize)
    {
        if(pageSize == 0)
        {
            pageSize = 20;
        }
        List<ShowUserResponse> showTrainers = new ArrayList<>(converter.convertUserListToShowUserResponseCollection(userService.findAllUsersByRole(pageNumber, pageSize, roleService.findRoleByName(ERole.ROLE_TRAINER)).getContent()));
        return ResponseEntity.ok(showTrainers);
    }

    @GetMapping("/trainer/{profileName}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showBasicUsers(@PathVariable String profileName)
    {
        User user = userService.findUserByProfileName(profileName);
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Change active for trainer")
    @PatchMapping("/trainer/{profileName}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setActiveValue(@PathVariable String profileName)
    {
        return usersService.changeActive(profileName);
    }

    @ApiOperation(value = "Create schedule for user, you must use date format: " + DATE_FORMAT)
    @PostMapping("/trainer/{profileName}/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> setSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest, @PathVariable String profileName)
    {
        if(scheduleRequest.getStartDate().size() == scheduleRequest.getEndDate().size())
        {
            scheduleService.saveScheduleList(scheduleRequest.getStartDate(), scheduleRequest.getEndDate(), userService.findUserByProfileName(profileName));
            return ResponseEntity.ok(new MessageResponse("Success"));
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/trainer/{profileName}/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showSchedule(@PathVariable String profileName)
    {
        List<Schedule> scheduleList = scheduleService.findScheduleByUser(userService.findUserByProfileName(profileName));
        return ResponseEntity.ok(scheduleList);
    }

    @PatchMapping("/trainer/{profileName}/schedule")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update schedule for user, you must use date format: " + DATE_FORMAT)
    public ResponseEntity<?> updateSchedule(@Valid @RequestBody ScheduleUpdateRequest scheduleUpdateRequest, @PathVariable String profileName)
    {
        if(scheduleUpdateRequest.getOldStartDate().size() == scheduleUpdateRequest.getOldEndDate().size() && scheduleUpdateRequest.getNewEndDate().size() == scheduleUpdateRequest.getNewStartDate().size())
        {
            User user = userService.findUserByProfileName(profileName);
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
