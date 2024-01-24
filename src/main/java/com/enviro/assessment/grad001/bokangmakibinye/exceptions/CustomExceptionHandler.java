package com.enviro.assessment.grad001.bokangmakibinye.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NegativeRequestAmountException.class)
    public ResponseEntity<String> handleNegativeRequestAmountException(NegativeRequestAmountException negativeRequestAmountException) {
        return new ResponseEntity<>("Negative Request Amount Exception Occurred: " + 
        negativeRequestAmountException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateCheckFailedException.class)
    public ResponseEntity<String> handleDateCheckFailedException(DateCheckFailedException dateCheckFailedException) {
        return new ResponseEntity<>("Date Check Exception Occurred: " + 
        dateCheckFailedException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestedAmountExceedsMaxAllowedException.class)
    public ResponseEntity<String> handleRequestedAmountExceedsMaxAllowedException(RequestedAmountExceedsMaxAllowedException requestedAmountExceedsMaxAllowedException) {
        return new ResponseEntity<>("Requested amount Exception Occurred: " + 
        requestedAmountExceedsMaxAllowedException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException notFoundException) {
        return new ResponseEntity<>("RNot found Exception Occurred: " + 
        notFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Add more exception handlers as needed
}

