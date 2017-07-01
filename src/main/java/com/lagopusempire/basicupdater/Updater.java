package com.lagopusempire.basicupdater;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Foomf
 */
public class Updater<V> {
    private final Map<V, Update<V>> updates = new HashMap<>();
    
    private final V currentVersion;
    
    public Updater(TaskExecutor executor, V currentVersion) {
        if(executor == null) {
            throw new IllegalArgumentException("Task executor cannot be null.");
        }
        
        if(currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        this.currentVersion = currentVersion;
    }
    
    public V DoUpdates(V expectedVersion) throws Exception {
        if(expectedVersion == null) {
            throw new IllegalArgumentException("Expected version cannot be null.");
        }
        
        V current = currentVersion;
        while(!current.equals(expectedVersion)) {
            Update<V> update = updates.get(current);
            if(update == null) {
                throw new Exception("Failed to update! Update from version " + current + " needed!");
            }
            current = update.doUpdate(current);
        }
        
        return current;
    }
    
    public void addUpdate(Update<V> update) {
        if(update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }
        
        updates.put(update.getPreReq(), update);
    }
}
