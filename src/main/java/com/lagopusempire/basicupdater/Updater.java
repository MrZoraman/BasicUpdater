package com.lagopusempire.basicupdater;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * @author Foomf
 */
public class Updater<V> {
    private final Map<V, Update<V>> updates = new HashMap<>();
    
    private final V currentVersion;
    
    private Optional<UpdateFailure> failure = Optional.empty();
    
    public Updater(TaskExecutor executor, V currentVersion) {
        if(executor == null) {
            throw new IllegalArgumentException("Task executor cannot be null.");
        }
        
        if(currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        this.currentVersion = currentVersion;
    }
    
    public Optional<V> DoUpdates(V expectedVersion) {
        if(expectedVersion == null) {
            throw new IllegalArgumentException("Expected version cannot be null.");
        }
        
        failure = Optional.empty();
        
        Optional<V> current = Optional.of(currentVersion);
        while(current.isPresent() && !current.get().equals(expectedVersion)) {
            Update<V> update = updates.get(current.get());
            if(update == null) {
                failure = Optional.of(new UpdateFailure(FailReason.MISSING_UPDATE_DEPENDENCIES, "Update from version " + current + " needed!"));
                //throw new Exception("Failed to update! Update from version " + current + " needed!");
                return Optional.empty();
            }
            current = update.doUpdate(current.get());
        }
        
        return current;
    }
    
    public void addUpdate(Update<V> update) {
        if(update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }
        
        updates.put(update.getPreReq(), update);
    }
    
    public Optional<UpdateFailure> getFailInfo() {
        return failure;
    }
}
