package com.api.gym.exception;

import com.api.gym.payload.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CouponNotFoundAdvice
{
    @ResponseBody
    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<MessageResponse> userNotFoundHandler(CouponNotFoundException exception)
    {
        return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.valueOf(500));
    }
}
