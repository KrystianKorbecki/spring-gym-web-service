package com.api.gym.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanExerciseRequest
{
    private List<Double> proposeWeight;
    private List<Integer> proposeRepeat;
    private List<Integer> proposeRest;
    private String description;
    private String nameExercise;
}
