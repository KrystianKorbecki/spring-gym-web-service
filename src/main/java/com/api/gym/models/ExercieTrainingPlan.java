package com.api.gym.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "exercise_training_plan", schema = "public")
public class ExercieTrainingPlan
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_training_plan")
    private Long idTrainingPlan;

    @Column(name = "id_exercise")
    private Long idExercise;

    @Column(name = "weight")
    @ElementCollection(targetClass= Double.class)
    private List<Double> weight;

    @Column(name = "weight")
    @ElementCollection(targetClass= Integer.class)
    private List<Integer> repeat;

    @Column(name = "date_of_occurrence")
    private Date DateOfOccurence;

    @Column(name = "duration")
    private Time duration;
}
