package com.lagopusempire.basicupdater;

public class CircularUpdateException extends RuntimeException {
    public CircularUpdateException(String message) {
        super(message);
    }
}
