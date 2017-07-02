package com.lagopusempire.basicupdater;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author Foomf
 */
public class Updater<V, U> {

    private final Map<V, Update<V, U>> updates = new HashMap<>();
    private final Set<V> usedVersions = new HashSet<>();
    
    private Optional<V> missingVersion = Optional.empty();
    
    LinkedList<U> getUpdatesTo(V currentVersion, V expectedVersion) {
        if (currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        if (expectedVersion == null) {
            throw new IllegalArgumentException("Expected version cannot be null.");
        }
        
        //base case
        if(currentVersion.equals(expectedVersion)) {
            usedVersions.clear();
            usedVersions.add(currentVersion);
            return new LinkedList<>();
        }
        
        Update<V, U> update = updates.get(currentVersion);
        if(update == null) {
            missingVersion = Optional.of(currentVersion);
            throw new UpdateMissingException("Update version " 
                    + currentVersion + " is missing!");
        }
        
        V next = update.getOutput();
        if(!usedVersions.add(next)) {
            throw new CircularUpdateException("Your have a loop in your updates."
                + " Loop detected at " + currentVersion + " -> " + next + ".");
        }
        
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
    
    public Optional<V> getMissingVersion() {
        return missingVersion;
    }
}
