package com.api.gym.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon", schema = "public")
public class Coupon
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "discount")
    private int discount;

    @Column(name = "discount_percent")
    private int discountPercent;

    @NotBlank
    @Column(name = "name", unique = true)
    @Size(max = 20)
    private String name;

    @Column(name = "code", unique = true)
    private String code;

}
