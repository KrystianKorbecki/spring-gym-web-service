package com.api.gym.exception;

import com.api.gym.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class DatabaseExceptionAdvice
{
    @ResponseBody
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<MessageResponse> databaseError()
    {
        return new ResponseEntity<>(new MessageResponse("Database error"), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
