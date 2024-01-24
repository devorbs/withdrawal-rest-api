package com.enviro.assessment.grad001.bokangmakibinye.exceptions;

public class RequestedAmountExceedsMaxAllowedException extends RuntimeException {
    public RequestedAmountExceedsMaxAllowedException(String message) {
        super(message);
    }
}
