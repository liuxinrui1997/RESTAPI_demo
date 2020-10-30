package com.demo.controller;

import com.demo.config.PlanAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PlanAlreadyExistExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(PlanAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String PlanAlreadyExistExceptionHandler(PlanAlreadyExistException ex) {
        return ex.getMessage();
    }
}
