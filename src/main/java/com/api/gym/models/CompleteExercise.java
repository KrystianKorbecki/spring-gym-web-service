package com.api.gym.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "complete_exercise", schema = "public")
public class CompleteExercise
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_training_plan_exercise", nullable=false)
    private TrainingPlanExercise trainingPlanExercise;

    @Column(name = "duration")
    private Time duration;

    @Column(name = "weight")
    @ElementCollection(targetClass= Double.class)
    private List<Double> weight;

    @Column(name = "series")
    @ElementCollection(targetClass= Short.class)
    private List<Short> series;

    @Column(name = "rest")
    @ElementCollection(targetClass= Time.class)
    private List<Time> rest;

    @Column(name = "note")
    private String note;
}
