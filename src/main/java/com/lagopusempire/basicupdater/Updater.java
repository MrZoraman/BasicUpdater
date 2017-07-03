package com.lagopusempire.basicupdater;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import org.javatuples.Pair;

/**
 * This class takes a list of {@link com.lagopusempire.basicupdater.Update updates}
 * and returns the order in which they must be run in order to execute correctly.
 * 
 * @author Foomf
 * 
 * @param <V> The version type used. This must have a working equals() method
 * to work, as that is how the updater compares versions to check what updates
 * are needed. Integers work well, as well something like semvers. Floats,
 * doubles, etc are ill-advised.
 * @param <U> This is the type that will contain the instructions needed for
 * updating the system. This can be something as simple as {@link java.lang.Runnable},
 * but it can be any type you want. It is up to the user of the library to know
 * how to call the right methods in U.
 */
public class Updater<V, U> {

    /**
     * This is the data structure holding the list of updates. The key is
     * the "from" version (prerequisite), and the value is the update itself.
     */
    private final Map<V, Update<V, U>> updates = new HashMap<>();
    
    /**
     * Gets the list of {@link com.lagopusempire.basicupdater.Update updates} 
     * needed to go from the current version to the desired version.
     * @param currentVersion The current version of the system that needs updating.
     * @param desiredVersion The desired version to update the system to.
     * @return A list of updates that will take the system from the current version
     * to the desired version. If there are no updates to be done, then the
     * list will be empty. It will never return null.
     * 
     * @throws IllegalArgumentException If any of the arguments are null
     * @throws UpdateMissingException If there are insufficient updates to
     * go from the current version to the desired version.
     * @throws CircularUpdateException If there is any sort of cycle in the
     * "directed acyclic graph" of updates provided by 
     * {@link #addUpdate(com.lagopusempire.basicupdater.Update) addUpdate()}.
     */
    public LinkedList<Update<V, U>> getUpdatesTo(V currentVersion, V desiredVersion) {
        if (currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        if (desiredVersion == null) {
            throw new IllegalArgumentException("Expected version cannot be null.");
        }
        
        return resolveUpdateOrder(currentVersion, desiredVersion, new HashSet<>());
    }
    
    /**
     * Internal update order resolver function. It contains a third parameter
     * for the set of used versions, and has less bounds checking on the inputs
     * (without assertions enabled).
     * @param currentVersion The current version of the system that needs updating.
     * @param desiredVersion The desired version to update the system to.
     * @param usedVersions This is a set versions that have already been "used."
     * If the update resolver comes across a version that the system has already
     * reached before, then it fails, as it has detected a cycle.
     * @return A list of updates that will take the system from the current version
     * to the desired version. If there are no updates to be done, then the
     * list will be empty. It will never return null.
     * 
     * @throws UpdateMissingException If there are insufficient updates to
     * go from the current version to the desired version.
     * @throws CircularUpdateException If there is any sort of cycle in the
     * "directed acyclic graph" of updates provided by 
     * {@link #addUpdate(com.lagopusempire.basicupdater.Update) addUpdate()}.
     */
    private LinkedList<Update<V, U>> resolveUpdateOrder(V currentVersion, V desiredVersion, 
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
        
        LinkedList<Update<V, U>> updateList = resolveUpdateOrder(
                to, desiredVersion, usedVersions);
        
        //updateList.addFirst(update.getUpdate());
        updateList.addFirst(update);
        return updateList;
    }

    /**
     * Adds an {@link com.lagopusempire.basicupdater.Update} to the updater.
     * @param update The update to add. Each update contains some data the
     * updater will use to organize the updates into the correct order.
     * 
     * @throws IllegalArgumentException If the update is null.
     */
    public void addUpdate(Update<V, U> update) {
        if (update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }

        updates.put(update.getFrom(), update);
    }
}
