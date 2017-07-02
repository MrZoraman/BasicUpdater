package com.lagopusempire.basicupdater;

public class CircularUpdateException extends RuntimeException {
    private final Update<?,?> circularExceptionCause;
    
    CircularUpdateException(Update circularExceptionCause) {
        super("Your have a loop in your updates. Loop detected at " 
                + circularExceptionCause.getFrom() + " -> " 
                + circularExceptionCause.getTo() + ".");
        this.circularExceptionCause = circularExceptionCause;
    }
    
    public Update<?,?> getCircularExceptionCause() {
        return circularExceptionCause;
    }
}
