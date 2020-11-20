package com.api.gym.controllers.admin;

import com.api.gym.models.ERole;
import com.api.gym.models.User;
import com.api.gym.payload.request.ChangeActive;
import com.api.gym.payload.request.EmailRequest;
import com.api.gym.payload.response.ShowUserResponse;
import com.api.gym.service.UserRepositoryService;
import com.api.gym.service.UsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminUsersController
{
    private final UsersService usersService;
    private final UserRepositoryService userRepositoryService;


    AdminUsersController(UsersService usersService, UserRepositoryService userRepositoryService)
    {
        this.usersService = usersService;
        this.userRepositoryService = userRepositoryService;
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> showUsers(@RequestParam(value = "email", required = false) String email)
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

    @ApiOperation(value = "Change active for trainer")
    @PatchMapping("/user/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>setActiveValue(@Valid @RequestBody ChangeActive changeActive)
    {
        return usersService.changeActive(changeActive);
    }

    @ApiOperation(value = "Change active for trainer")
    @DeleteMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>deleteUser(@Valid @RequestBody EmailRequest emailRequest)
    {
        return ResponseEntity.ok(userRepositoryService.deleteByEmail(emailRequest.getEmail()));
    }
}
