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
    
    public LinkedList<U> getUpdatesTo(V currentVersion, V expectedVersion) {
        usedVersions.clear();
        return resolveUpdateOrder(currentVersion, expectedVersion);
    }
    
    private LinkedList<U> resolveUpdateOrder(V currentVersion, V expectedVersion) {
        if (currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        if (expectedVersion == null) {
            throw new IllegalArgumentException("Expected version cannot be null.");
        }
        
        //base case
        if(currentVersion.equals(expectedVersion)) {
            usedVersions.add(currentVersion);
            return new LinkedList<>();
        }
        
        if(!updates.containsKey(currentVersion)) {
            missingVersion = Optional.of(currentVersion);
            throw new UpdateMissingException("Update version " 
                    + currentVersion + " is missing!");
        }
        
        usedVersions.add(currentVersion);
        
        Update<V, U> update = updates.get(currentVersion);
        
        V to = update.getTo();
        if(usedVersions.contains(to)) {
            throw new CircularUpdateException("Your have a loop in your updates."
                + " Loop detected at " + currentVersion + " -> " + to + ".");
        }
        
        LinkedList<U> updateQueue = resolveUpdateOrder(to, expectedVersion);
        updateQueue.addFirst(update.getUpdate());
        
        return updateQueue;
    }

    public void addUpdate(Update<V, U> update) {
        if (update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }

        updates.put(update.getFrom(), update);
    }
    
    public Optional<V> getMissingVersion() {
        return missingVersion;
    }
}
