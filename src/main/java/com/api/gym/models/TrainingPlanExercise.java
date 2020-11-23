package com.api.gym.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training_plan_exercise", schema = "public")
public class TrainingPlanExercise
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_training_plan", nullable=false)
    private TrainingPlan trainingPlan;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_exercise", referencedColumnName = "id")
    private Exercise exercise;

    @Column(name = "propose_weight")
    @ElementCollection(targetClass= Double.class)
    private List<Double> proposeWeight;

    @Column(name = "propose_series")
    @ElementCollection(targetClass= Short.class)
    private List<Short> proposeSeries;

    @Column(name = "propose_repeat")
    @ElementCollection(targetClass= Short.class)
    private List<Short> proposeRepeat;

    @Column(name = "propose_rest")
    @ElementCollection(targetClass= Time.class)
    private List<Time> proposeRest;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompleteExercise> completeExercises = new ArrayList<>();
}
