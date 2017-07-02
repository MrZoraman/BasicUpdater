package com.lagopusempire.basicupdater;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Foomf
 */
public class Updater<V, U> {

    private final Map<V, Update<V, U>> updates = new HashMap<>();
    
    public LinkedList<U> getUpdatesTo(V currentVersion, V desiredVersion) {
        if (currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        if (desiredVersion == null) {
            throw new IllegalArgumentException("Expected version cannot be null.");
        }
        
        return resolveUpdateOrder(currentVersion, desiredVersion, new HashSet<>());
    }
    
    private LinkedList<U> resolveUpdateOrder(V currentVersion, V desiredVersion, 
            Set<V> usedVersions) {
        assert(currentVersion != null);
        assert(desiredVersion != null);
        
        usedVersions.add(currentVersion);
        
        //base case
        if(currentVersion.equals(desiredVersion)) {
            return new LinkedList<>();
        }
        
        if(!updates.containsKey(currentVersion)) {
            throw new UpdateMissingException(currentVersion);
        }
        
        Update<V, U> update = updates.get(currentVersion);
        //This should never be null because of the checks in addUpdate().
        assert(update != null);
        V to = update.getTo();
        
        if(usedVersions.contains(to)) {
            throw new CircularUpdateException(update);
        }
        
        LinkedList<U> updateList = resolveUpdateOrder(
                to, desiredVersion, usedVersions);
        
        updateList.addFirst(update.getUpdate());
        return updateList;
    }

    public void addUpdate(Update<V, U> update) {
        if (update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }

        updates.put(update.getFrom(), update);
    }
}
