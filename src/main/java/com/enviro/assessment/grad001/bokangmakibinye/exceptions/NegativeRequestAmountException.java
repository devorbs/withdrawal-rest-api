package com.enviro.assessment.grad001.bokangmakibinye.exceptions;

public class NegativeRequestAmountException extends RuntimeException {
    public NegativeRequestAmountException(String message) {
        super(message);
    }
}
