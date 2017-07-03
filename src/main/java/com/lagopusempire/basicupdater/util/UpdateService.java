package com.lagopusempire.basicupdater.util;

import com.lagopusempire.basicupdater.Update;
import com.lagopusempire.basicupdater.Updater;
import java.util.List;

/**
 *
 * @author Foomf
 */
public class UpdateService<V, U> {
    private final Updater<V, U> updater = new Updater<>();
    
    private final UpdateRunner<V, U> updateRunner;
    
    public UpdateService(UpdateExecutor<U> updateExecutor) {
        this.updateRunner = new UpdateRunner(updateExecutor);
    }
    
    public void addUpdate(Update<V, U> update) {
        updater.addUpdate(update);
    }
    
    public V doUpdates(V currentVersion, V desiredVersion) {
        List<Update<V, U>> updates = updater.getUpdatesTo(currentVersion, desiredVersion);
        return updateRunner.executeUpdates(currentVersion, updates);
    }
}
