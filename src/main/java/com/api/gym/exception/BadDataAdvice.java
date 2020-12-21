package com.api.gym.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BadDataAdvice
{
    @ResponseBody
    @ExceptionHandler(BadDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String roleNotFoundHandler(ExerciseNotFoundException exception)
    {
        return exception.getMessage();
    }
}
