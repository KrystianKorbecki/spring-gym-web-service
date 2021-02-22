package com.api.gym.controllers.admin;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminController
{

    @ApiOperation(value = "Show main site for admin")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String,Integer>>adminMainSite()
    {
        Map<String, Integer> response = new LinkedHashMap<>();
        response.put("newUsersToday", 1000);
        response.put("soldTicketsToday", 100);
        response.put("newUsersToday", 50);
        response.put("newUsersAtMonth", 500);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
