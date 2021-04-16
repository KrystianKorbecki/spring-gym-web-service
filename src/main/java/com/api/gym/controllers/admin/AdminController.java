package com.api.gym.controllers.admin;

import com.api.gym.controllers.BasicUserController;
import com.api.gym.payload.response.MainAdminData;
import com.api.gym.service.users.AdminsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin")
public class AdminController
{
    private final AdminsService adminsService;

    public AdminController(AdminsService adminsService)
    {
        this.adminsService = adminsService;
    }

    /**
     * This method shows main data for admin with {@link AdminsService#primaryDataForAdmin()}
     * @return
     */
    @ApiOperation(value = "Show main site for admin")
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CollectionModel<MainAdminData>>adminMainSite()
    {
        Link selfRelLink = linkTo(methodOn(AdminController.class).adminMainSite()).withSelfRel();
        Link basicUsersListLink = linkTo(methodOn(AdminBasicsController.class).showBasicUsers(1, 20)).withRel("basicUser");
        Link trainersListLink = linkTo(methodOn(AdminTrainersController.class).showTrainers(1, 20)).withRel("trainerUsers");
        Link usersListLink = linkTo(methodOn(AdminUsersController.class).showBasicUsers(1, 20)).withRel("users");
        CollectionModel<MainAdminData> collectionModel = CollectionModel.of(adminsService.primaryDataForAdmin()).add(selfRelLink, basicUsersListLink, trainersListLink, usersListLink);
        return ResponseEntity.ok(collectionModel);
    }

}
