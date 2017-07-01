package com.lagopusempire.basicupdater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Foomf
 */
public class Updater<V, U> {

    private final Map<V, Update<V, U>> updates = new HashMap<>();
    private final List<Update<V, U>> updateList = new ArrayList<>();

    //private final V currentVersion;

    //private Optional<UpdateFailure> failure = Optional.empty();

//    public Updater(TaskExecutor executor) {
//        if (executor == null) {
//            throw new IllegalArgumentException("Task executor cannot be null.");
//        }
//
//        //this.currentVersion = currentVersion;
//    }
    
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
            throw new IllegalStateException("fix me");
        }
        
        V next = update.getOutput();
        LinkedList<U> updateQueue = getUpdatesTo(next, expectedVersion);
        updateQueue.addFirst(update.getUpdate());
        
        return updateQueue;
        
        
        //return null;
    }

//    private void resolve(V expectedVersion) {
//        if (expectedVersion == null) {
//            throw new IllegalArgumentException("Expected version cannot be null.");
//        }
//        
//        
//    }

//    public Optional<V> DoUpdates(V expectedVersion) {
//        if (expectedVersion == null) {
//            throw new IllegalArgumentException("Expected version cannot be null.");
//        }
//
//        failure = Optional.empty();
//
//        Optional<V> current = Optional.of(currentVersion);
//        while (current.isPresent() && !current.get().equals(expectedVersion)) {
//            Update<V> update = updates.get(current.get());
//            if (update == null) {
//                failure = Optional.of(new UpdateFailure(
//                        FailReason.MISSING_UPDATE_DEPENDENCIES,
//                        "Update from version " + current + " needed!"));
//                //throw new Exception("Failed to update! Update from version " + current + " needed!");
//                return Optional.empty();
//            }
//            current = update.doUpdate(current.get());
//        }
//
//        return current;
//    }

    public void addUpdate(Update<V, U> update) {
        if (update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }

        updates.put(update.getPreReq(), update);
    }

//    public Optional<UpdateFailure> getFailInfo() {
//        return failure;
//    }
}
