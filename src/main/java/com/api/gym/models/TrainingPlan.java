package com.api.gym.models;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training_plan", schema = "public")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class TrainingPlan
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_user", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="id_trainer", nullable=false)
    private User trainer;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Type(type = "list-array")
    @Column(name = "propose_rest_exercise", columnDefinition = "integer[]")
    private List<Integer> proposeRestBetweenExercises;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainingPlanExercise> trainingPlanExercises = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompleteTrainingPlan> completeTrainingPlans = new ArrayList<>();
}
