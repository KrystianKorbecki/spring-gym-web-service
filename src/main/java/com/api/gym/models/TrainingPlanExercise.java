package com.api.gym.models;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
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

    @Type(type = "list-array")
    @Column(name = "propose_weight", columnDefinition = "numeric[]")
    private List<Double> proposeWeight;

    @Type(type = "list-array")
    @Column(name = "propose_repeat",  columnDefinition = "integer[]")
    private List<Integer> proposeRepeat;

    @Type(type = "list-array")
    @Column(name = "propose_rest",  columnDefinition = "integer[]")
    private List<Integer> proposeRest;

    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompleteExercise> completeExercises = new ArrayList<>();
}
