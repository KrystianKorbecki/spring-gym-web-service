package com.api.gym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TrainingPlanNotFoundAdvice
{
    @ResponseBody
    @ExceptionHandler(TrainingPlanNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String trainingPlanNotFoundHandler(TrainingPlanNotFoundException exception)
    {
        return exception.getMessage();
    }
}
