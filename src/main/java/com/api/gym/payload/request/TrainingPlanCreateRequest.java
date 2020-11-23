package com.api.gym.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanCreateRequest
{
   private String userEmail;
   private String dayOfWeek;
   private Time proposeRestBetweenExercises;
   private List<TrainingPlanExerciseRequest> trainingPlanExerciseRequests;

}
