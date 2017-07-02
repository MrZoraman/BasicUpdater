package com.lagopusempire.basicupdater;

/**
 * Represents a single update from one version to another. It contains the version
 * required for the update to happen, as well as the new version the system will
 * be after this update has been applied. Also contained are the update-specific
 * instructions needed to apply the update.
 * 
 * @author Foomf
 * 
 * @param <V> The version type used. This must have a working equals() method
 * to work, as that is how the updater compares versions to check what updates
 * are needed. Integers work well, as well something like semvers. Floats,
 * doubles, etc are ill-advised.
 * @param <U> This is the type that will contain the instructions needed for
 * updating the system. This can be something as simple as {@link java.lang.Runnable},
 * but it can be any type you want. It is up to the user of the library to know
 * how to call the right methods in U.
 */
public class Update<V, U> {
    /**
     * This is the version required for the update to run.
     */
    private final V from;
    /**
     * This is the version that the system will be when this update is complete.
     */
    private final V to;
    
    /**
     * This contains the instructions for performing the update.
     */
    private final U update;
    
    /**
     * Creates an update that will transition the system from version "from"
     * to version "to" as supplied in the parameters.
     * @param from The version the system is required to be at for the update
     * to run. It can be seen as a required prerequisite version.
     * @param to This is the version the system will be at <i>if</i> the update
     * completes successfully.
     * @param update The update-specific instructions for updating the system.
     * 
     * @throws IllegalArgumentException if any of the arguments are null, or if
     * "from" and "to" are equivalent.
     */
    public Update(V from, V to, U update) {
        if(from == null) {
            throw new IllegalArgumentException("Prerequisite version cannot be null.");
        }
        
        if(to == null) {
            throw new IllegalArgumentException("Output version cannot be null.");
        }
        
        if(update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }
        
        if(from.equals(to)) {
            throw new IllegalArgumentException(
                    "Prerequisite version cannot be the same as the result version.");
        }
        
        this.from = from;
        this.to = to;
        this.update = update;
    }
    
    /**
     * Gets the version required for the update.
     * @return The version required for the update. Will not be null.
     */
    public V getFrom() {
        return from;
    }
    
    /**
     * Gets the version that the system will be like if the update succeeds.
     * @return The version after the update is applied. Will not be null.
     */
    public V getTo() {
        return to;
    }
    
    /**
     * Gets the update instructions for applying the update.
     * @return The update-specific instructions. Will not be null.
     */
    public U getUpdate() {
        return update;
    }
}
