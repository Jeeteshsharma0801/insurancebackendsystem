package com.insurance.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    //Here we have add a custom exception
    @ExceptionHandler(InsuranceException.class)
    public ResponseEntity<?> insuranceException(InsuranceException ex) {
        // You can return a custom error response object if desired
        Map<String,String> expRes = new HashMap<>();
        expRes.put("errorCode", String.valueOf(ex.getCode()));
        expRes.put("message",ex.getMessage());
        return new ResponseEntity<>(expRes,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception ex) {
        // You can return a custom error response object if desired
        Map<String,String> expRes = new HashMap<>();
        expRes.put("message",ex.getMessage());
        return new ResponseEntity<>(expRes,HttpStatus.NOT_FOUND);
    }
}
