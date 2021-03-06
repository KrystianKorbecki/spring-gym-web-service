package com.api.gym.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_ticket", schema = "public")
public class UserTicket
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_ticket")
    @NotBlank
    private Long idTicket;

    @Column(name = "id_user")
    @NotBlank
    private Long idUser;

    @Column(name = "id_coupon")
    @NotBlank
    private Long idCoupon;

    @Column(name = "purchase_date")
    private Timestamp purchaseDate;

    @Column(name = "start_date")
    @NotBlank
    private Timestamp startDate;
}
