package com.springingdream.userhistory.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserHistoryNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(UserHistoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFound(UserHistoryNotFoundException e) {
        return e.getMessage();
    }
}
