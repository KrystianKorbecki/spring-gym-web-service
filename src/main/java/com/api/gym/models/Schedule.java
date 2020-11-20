package com.api.gym.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedule", schema = "public")
public class Schedule
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_user", nullable=false)
    private User user;

    @Column(name="start_date")
    @ElementCollection(targetClass=Timestamp.class)
    private List<Timestamp> startDate;

    @Column(name="end_date")
    @ElementCollection(targetClass=Timestamp.class)
    private List<Timestamp> endDate;

}
