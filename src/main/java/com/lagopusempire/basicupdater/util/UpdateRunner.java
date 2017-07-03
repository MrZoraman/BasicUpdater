package com.lagopusempire.basicupdater.util;

import com.lagopusempire.basicupdater.Update;
import java.util.List;
import java.util.Optional;

/**
 * This uses an {@link UpdateExecutor} to
 * procedurally run the updates provided by the 
 * {@link com.lagopusempire.basicupdater.Updater} class. It then returns the
 * version that was successfully updated to. If an error occurred during the
 * updates, the version returned will be the last version the updater reached
 * successfully.
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
 * 
 * @see UpdateExecutor
 */
public class UpdateRunner<V, U> {
    /**
     * This is the update executor. It is a functional interface that understands
     * how to execute updates of type U.
     */
    private final UpdateExecutor<U> updateExecutor;
    
    /**
     * Constructor.
     * @param updateExecutor The 
     * {@link com.lagopusempire.basicupdater.util.UpdateExecutor} that will
     * execute each update.
     */
    public UpdateRunner(UpdateExecutor<U> updateExecutor) {
        this.updateExecutor = updateExecutor;
    }
    
    /**
     * Runs the updates. While The 
     * {@link com.lagopusempire.basicupdater.Updater#getUpdatesTo(
     * java.lang.Object, java.lang.Object) Updater.getUpdatesTo()}
     * method returns a list of updates, it does not actually run them. This
     * class takes the list returned by that method and runs each update by
     * passing them to the given executor's 
     * {@link UpdateExecutor#doUpdate(java.lang.Object) UpdateExecutor.doUpdate(U)} 
     * method.
     * @param updates This is the list of updates produced by an
     * {@link com.lagopusempire.basicupdater.Updater}.
     * @return The latest version the update runner was able to reach before
     * an error occurred. If no errors occurred, then this will be the
     * desired version specified in the updater. If there were no updates to
     * be done, then the optional will be empty.
     * 
     * @throws IllegalArgumentException if the updates are null.
     */
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
