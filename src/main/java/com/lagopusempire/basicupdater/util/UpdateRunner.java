package com.lagopusempire.basicupdater.util;

import com.lagopusempire.basicupdater.Update;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Foomf
 */
public class UpdateRunner<V, U> {
    private final UpdateExecutor<U> updateExecutor;
    
    public UpdateRunner(UpdateExecutor<U> updateExecutor) {
        this.updateExecutor = updateExecutor;
    }
    
    public Optional<V> executeUpdates(List<Update<V, U>> updates) {
        if(updates == null) {
            throw new IllegalArgumentException("Updates cannot be null.");
        }
        
        Optional<V> newVersion = Optional.empty();
        for(Update<V, U> update : updates) {
            boolean success = updateExecutor.doUpdate(update.getUpdate());
            if(success) {
                newVersion = Optional.of(update.getTo());
            } else {
                break;
            }
        }
        return newVersion;
    }
}
