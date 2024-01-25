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
        return new ResponseEntity<>("Not found Exception Occurred: " + 
        notFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvestorIdMismatchException.class)
    public ResponseEntity<String> handleInvestorIdMismatchException(InvestorIdMismatchException investorIdMismatchException) {
        return new ResponseEntity<>("Investor Product Exception Occurred: " + 
        investorIdMismatchException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AgeLimitException.class)
    public ResponseEntity<String> handleAgeLimitException(AgeLimitException ageLimitException) {
        return new ResponseEntity<>("Age Limit Exception Occurred: " + 
        ageLimitException.getMessage(), HttpStatus.FORBIDDEN);
    }

}

