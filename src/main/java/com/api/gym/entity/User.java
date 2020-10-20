package com.api.gym.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = "public")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long idUser;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @NotBlank
    @Column(name = "phone_number", unique = true)
    private int phoneNumber;

    @NotBlank
    @Column(name = "email_address", unique = true)
    private String emailAddress;

    @NotBlank
    @Column(name = "password", unique = true)
    private String password;

    @NotBlank
    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "code")
    private String code;

    @Column(name = "id_trainer")
    private Long idTrainer;

    @Column(name = "id_admin")
    private Long idAdmin;


    @ManyToMany(mappedBy = "users")
    private Set<Role> roles;
}
