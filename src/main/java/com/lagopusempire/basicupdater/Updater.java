package com.lagopusempire.basicupdater;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Foomf
 */
public class Updater<V, U> {

    private final Map<V, Update<V, U>> updates = new HashMap<>();
    
    LinkedList<U> getUpdatesTo(V currentVersion, V expectedVersion) {
        if (currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        if (expectedVersion == null) {
            throw new IllegalArgumentException("Expected version cannot be null.");
        }
        
        //base case
        if(currentVersion.equals(expectedVersion)) {
            return new LinkedList<>();
        }
        
        Update<V, U> update = updates.get(currentVersion);
        if(update == null) {
            throw new UpdateMissingException("Update version " + currentVersion + " missing!");
        }
        
        V next = update.getOutput();
        LinkedList<U> updateQueue = getUpdatesTo(next, expectedVersion);
        updateQueue.addFirst(update.getUpdate());
        
        return updateQueue;
    }

    public void addUpdate(Update<V, U> update) {
        if (update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }

        updates.put(update.getPreReq(), update);
    }
}
