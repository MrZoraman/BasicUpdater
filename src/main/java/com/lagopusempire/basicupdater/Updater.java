package com.lagopusempire.basicupdater;

import com.github.zafarkhaja.semver.Version;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Foomf
 */
public class Updater {
    private final Map<Version, Update> updates = new HashMap<>();
    
    private final TaskExecutor executor;
    private final Version currentVersion;
    
    public Updater(TaskExecutor executor, Version currentVersion) {
        if(executor == null) {
            throw new IllegalArgumentException("Task executor cannot be null.");
        }
        
        if(currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        this.executor = executor;
        this.currentVersion = currentVersion;
    }
    
    public Version DoUpdates(Version expectedVersion) throws Exception {
        if(expectedVersion == null) {
            throw new IllegalArgumentException("Expected version cannot be null.");
        }
        
        Version current = currentVersion;
        while(!current.equals(expectedVersion)) {
            Update update = updates.get(current);
            if(update == null) {
                throw new Exception("Failed to update! Update from version " + current + " needed!");
            }
            current = update.doUpdate(current);
        }
        
        return current;
    }
    
    public void addUpdate(Update update) {
        if(update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }
        
        updates.put(update.getPreReq(), update);
    }
}
