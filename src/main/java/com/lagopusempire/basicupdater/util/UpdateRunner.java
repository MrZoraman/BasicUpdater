package com.lagopusempire.basicupdater.util;

import com.lagopusempire.basicupdater.Update;
import java.util.List;

/**
 *
 * @author Foomf
 */
public class UpdateRunner<V, U> {
    private final UpdateExecutor<U> updateExecutor;
    
    public UpdateRunner(UpdateExecutor<U> updateExecutor) {
        this.updateExecutor = updateExecutor;
    }
    
    public V executeUpdates(V currentVersion, List<Update<V, U>> updates) {
        if(currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        if(updates == null) {
            throw new IllegalArgumentException("Updates cannot be null.");
        }
        
        V newVersion = currentVersion;
        for(Update<V, U> update : updates) {
            boolean success = updateExecutor.doUpdate(update.getUpdate());
            if(success) {
                newVersion = update.getTo();
            } else {
                break;
            }
        }
        return newVersion;
    }
}
