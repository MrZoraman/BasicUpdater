package com.lagopusempire.basicupdater;

/**
 * This exception is thrown if the updater has no way of procedurally updating
 * to a desired version. This means that there are updates that are missing.
 * For instance, if the updater knows about an update from A -> B, and an update
 * from C -> D, then this exception will be thrown if it is asked to go from
 * A -> D, because the updater is missing an update(s) to go from B -> C.
 * This is another way of saying the system never reached the required
 * prerequisite version needed for an update to run.
 * 
 * @author Foomf
 */
public class UpdateMissingException extends RuntimeException {
    /**
     * This is the prerequisite version needed for the updating to continue.
     */
    private final Object missingVersion;
    
    /**
     * Constructor for internal use only.
     * @param missingVersion The prerequisite version the system was never
     * able to reach, thus implying missing updates.
     */
    UpdateMissingException(Object missingVersion) {
        super("Update version " + missingVersion + " is missing!");
        this.missingVersion = missingVersion;
    }
    
    /**
     * Gets the version that is missing the update. For example, if the updates
     * A -> B and C -> D are present, then this would return version B, as
     * there is no update from B -> C. As exceptions cannot
     * use generics, it will be up to the library user to cast to the
     * correct generic type.
     * @return The last version the updater was able to get to before it reached
     * a "dead end."
     */
    public Object getMissingVersion() {
        return missingVersion;
    }
}
