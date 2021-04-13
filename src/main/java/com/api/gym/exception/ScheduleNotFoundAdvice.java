package com.api.gym.exception;

import com.api.gym.payload.response.MessageResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ScheduleNotFoundAdvice
{
    @ResponseBody
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<?> scheduleNotFoundHandler(ScheduleNotFoundException exception)
    {
        return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.valueOf(404));

    }
}
