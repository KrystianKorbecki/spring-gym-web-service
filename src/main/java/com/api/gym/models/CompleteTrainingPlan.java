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
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "complete_training_plan", schema = "public")
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class CompleteTrainingPlan
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_training_plan", nullable=false)
    private TrainingPlan trainingPlan;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Type(type = "list-array")
    @Column(name = "rest_exercise", columnDefinition = "integer[]")
    private List<Integer> restBetweenExercise;
}
