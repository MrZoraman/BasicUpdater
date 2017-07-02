package com.lagopusempire.basicupdater;

/**
 *
 * @author Foomf
 */
public class UpdateMissingException extends RuntimeException {
    private final Object missingVersion;
    
    UpdateMissingException(Object missingVersion) {
        super("Update version " + missingVersion + " is missing!");
        this.missingVersion = missingVersion;
    }
    
    public Object getMissingVersion() {
        return missingVersion;
    }
}
