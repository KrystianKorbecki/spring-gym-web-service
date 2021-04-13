package com.api.gym.models;

import com.api.gym.enums.EVoivodeship;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "city")
    @NotBlank
    @Size(min = 2, max = 70)
    String city;

    @Column(name = "street")
    @Size(min = 2, max = 70)
    String street;

    @Column(name = "house_number")
    @NotBlank
    @Size(min = 1, max = 10)
    String houseNumber;

    @Column(name = "apartment_number")
    @Size(min = 1, max = 10)
    String apartmentNumber;

    @Column(name = "voivodeship")
    @NotBlank
    @Enumerated(EnumType.STRING)
    EVoivodeship voivodeship;

    @Column(name = "postal_code")
    @NotBlank
    String postalCode;
}
