package com.lagopusempire.basicupdater;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Foomf
 */
public class Updater<T> {
    private final Map<T, Update> updates = new HashMap<>();
    
    private final T currentVersion;
    
    public Updater(TaskExecutor executor, T currentVersion) {
        if(executor == null) {
            throw new IllegalArgumentException("Task executor cannot be null.");
        }
        
        if(currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        this.currentVersion = currentVersion;
    }
    
    public T DoUpdates(T expectedVersion) throws Exception {
        if(expectedVersion == null) {
            throw new IllegalArgumentException("Expected version cannot be null.");
        }
        
        T current = currentVersion;
        while(!current.equals(expectedVersion)) {
            Update<T> update = updates.get(current);
            if(update == null) {
                throw new Exception("Failed to update! Update from version " + current + " needed!");
            }
            current = update.doUpdate(current);
        }
        
        return current;
    }
    
    public void addUpdate(Update<T> update) {
        if(update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }
        
        updates.put(update.getPreReq(), update);
    }
}
