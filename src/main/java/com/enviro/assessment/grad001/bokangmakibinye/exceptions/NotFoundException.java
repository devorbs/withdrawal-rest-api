package com.enviro.assessment.grad001.bokangmakibinye.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class NotFoundException extends RuntimeException {
    
    public NotFoundException(String message, EntityNotFoundException ex) {
        super(message, ex);
    }
}
