package com.api.gym.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingPlanCreateRequest
{
   @Email
   private String userEmail;

   private String dayOfWeek;
   private List<Integer> proposeRestBetweenExercises;
   private List<TrainingPlanExerciseRequest> trainingPlanExerciseRequests;

}
