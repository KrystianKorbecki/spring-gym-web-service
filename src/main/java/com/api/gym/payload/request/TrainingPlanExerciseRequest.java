package com.api.gym.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanExerciseRequest
{
    private List<Double> proposeWeight;
    private List<Short> proposeSeries;
    private List<Short> proposeRepeat;
    private List<Time> proposeRest;
    private String description;
    private String nameExercise;
}
