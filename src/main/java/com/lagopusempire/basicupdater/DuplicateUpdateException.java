package com.lagopusempire.basicupdater;

/**
 * This exception is thrown if there are multiple updates that start
 * from the same version. It is probably a code/configuration smell
 * if this is happening. The BasicUpdater does not support any sort
 * of parallel updating.
 * @author Foomf
 */
public class DuplicateUpdateException extends RuntimeException {
    private final Update<?, ?> presentUpdate;
    private final Update<?, ?> attemptedUpdateAddition;
    
    /**
     * Constructor for internal use only.
     * @param updateA The first conflicting update.
     * @param updateB The second conflicting update.
     */
    DuplicateUpdateException(Update<?, ?> presentUpdate, 
            Update<?, ?> attemptedUpdateAddition) {
        assert(presentUpdate != null);
        assert(attemptedUpdateAddition != null);
        
        this.presentUpdate = presentUpdate;
        this.attemptedUpdateAddition = attemptedUpdateAddition;
    }
    
    /**
     * Gets the first conflicting update. This update has already been added
     * successfully to the {@link Updater}. However, once the updater throws
     * this exception, it removes this update from its map of updates. It is up
     * to the handler of this exception to decide which update should
     * be added to the updater. As exceptions cannot
     * use generics, it will be up to the library user to cast to the
     * correct generic type.
     * @return The first conflicting update. This is the update that was
     * added to the Updater first.
     */
    public Update<?, ?> getPresentUpdate() {
        return presentUpdate;
    }
    
    /**
     * Gets the second conflicting update. It was the addition of this
     * update that caused this exception to throw. When this exception is thrown,
     * this update is not added to the updater's map. Instead, it is up to the
     * handler of this exception to decide which update should be added
     * to the updater. As exceptions cannot
     * use generics, it will be up to the library user to cast to the
     * correct generic type.
     * @return The second conflicting update. This is the update was added
     * while another conflicting update had already been added prior.
     */
    public Update<?, ?> getAttemptedUpdateAddition() {
        return attemptedUpdateAddition;
    }
}
