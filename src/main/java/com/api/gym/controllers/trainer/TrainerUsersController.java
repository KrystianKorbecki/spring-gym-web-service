package com.api.gym.controllers.trainer;

import com.api.gym.models.ERole;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangeActive;
import com.api.gym.payload.request.EmailRequest;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.service.UserRepositoryService;
import com.api.gym.service.UsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trainer")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TrainerUsersController
{
    private final UsersService usersService;
    private final UserRepositoryService userRepositoryService;

    TrainerUsersController(UsersService usersService, UserRepositoryService userRepositoryService)
    {
        this.usersService = usersService;
        this.userRepositoryService = userRepositoryService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?> showMyUser(@RequestParam(value = "email", required = false) String email)
    {
        if(email == null)
        {
            List<ShowUserResponse> showUsers = new ArrayList<>(usersService.findUsersByRole(ERole.ROLE_USER));
            return ResponseEntity.ok(showUsers);
        }
        else
        {
            User user = userRepositoryService.findUserByEmail(email);
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping("/user")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?>deleteBasic(@Valid @RequestBody EmailRequest emailRequest)
    {
        return ResponseEntity.ok(userRepositoryService.deleteByEmail(emailRequest.getEmail()));
    }

    @ApiOperation(value = "Change active for user")
    @PatchMapping("/user/active")
    @PreAuthorize("hasRole('TRAINER')")
    public ResponseEntity<?>setActiveValue(@Valid @RequestBody ChangeActive changeActive)
    {
        return usersService.changeActive(changeActive);
    }

}
