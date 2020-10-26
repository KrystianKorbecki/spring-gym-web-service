package com.api.gym.controllers.admin;

import com.api.gym.exceptions.RoleNotFoundException;
import com.api.gym.models.ERole;
import com.api.gym.models.Role;
import com.api.gym.repository.RoleRepository;
import com.api.gym.repository.UserRepository;
import com.api.gym.security.services.UserDetailsImpl;
import com.api.gym.security.services.UserDetailsServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminController
{
    UserRepository userRepository;
    RoleRepository roleRepository;
    AdminController(UserRepository userRepository, RoleRepository roleRepository)
    {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @ApiOperation(value = "Show main site for admin")
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,String>>adminMainSite()
    {
        UserDetailsImpl authUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, String> response = new LinkedHashMap<>();
        response.put("name", authUser.getUsername());
        response.put("newUsersToday", "1000");
        response.put("soldTicketsToday", "100");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


//    @GetMapping("/trainer")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?>addTrainer(String code)
//    {
//        Set<Role> roles = new HashSet<>();
//        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                .orElseThrow(() -> new RoleNotFoundException(ERole.ROLE_ADMIN.toString()));
//        roles.add(adminRole);
//        return new ResponseEntity<>(userRepository.findAllByRoles(roles), HttpStatus.OK);
//    }
}
