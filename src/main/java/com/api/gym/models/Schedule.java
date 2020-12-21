package com.api.gym.models;

import com.api.gym.converters.DateConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_user", nullable=false)
    private User user;

    @Column(name="start_date")
    @Convert(converter = DateConverter.class)
    private LocalDateTime startDate;

    @Convert(converter = DateConverter.class)
    @Column(name="end_date")
    private LocalDateTime endDate;

}
