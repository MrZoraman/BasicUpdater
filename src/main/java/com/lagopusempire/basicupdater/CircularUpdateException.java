package com.lagopusempire.basicupdater;

/**
 * This exception is thrown if there are circular dependencies in the update list.
 * If {@link com.lagopusempire.basicupdater.Updater} detects a circular 
 * dependency while resolving the update order in 
 * {@link com.lagopusempire.basicupdater.Updater#getUpdatesTo(java.lang.Object, java.lang.Object)},
 * then a CircularUpdateException is thrown, which contains a reference to the 
 * update that caused the updater to detect a circular dependency.
 * 
 * If there is more than one circular dependency in the update list, this
 * exception will only contain the first one the updater came across.
 * @author Foomf
 */
public class CircularUpdateException extends RuntimeException {
    /**
     * This is the update that causes there to be a circular dependency.
     */
    private final Update<?,?> circularExceptionCause;
    
    /**
     * Constructor for internal use only.
     * @param circularExceptionCause The update that caused the circular
     * dependency to be detected.
     */
    CircularUpdateException(Update circularExceptionCause) {
        super("Your have a loop in your updates. Loop detected at " 
                + circularExceptionCause.getFrom() + " -> " 
                + circularExceptionCause.getTo() + ".");
        assert(circularExceptionCause != null);
        this.circularExceptionCause = circularExceptionCause;
    }
    
    /**
     * Gets the update that caused the exception. As exceptions cannot
     * use generics, it will be up to the library user to cast to the
     * correct generic type.
     * @return The offending update. Will not be null.
     */
    public Update<?,?> getCircularExceptionCause() {
        return circularExceptionCause;
    }
}
