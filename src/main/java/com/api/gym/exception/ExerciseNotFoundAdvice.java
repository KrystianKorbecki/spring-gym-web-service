package com.api.gym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExerciseNotFoundAdvice
{
    @ResponseBody
    @ExceptionHandler(ExerciseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String roleNotFoundHandler(ExerciseNotFoundException exception)
    {
        return exception.getMessage();
    }
}
