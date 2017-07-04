package com.lagopusempire.basicupdater;

/**
 * This interface contains instructions on how to execute an update.
 * @author Foomf
 * 
 * @param <U> This is the type that will contain the instructions needed for
 * updating the system. This can be something as simple as {@link java.lang.Runnable},
 * but it can be any type you want. It is up to the user of the library to know
 * how to call the right methods in U.
 */
@FunctionalInterface
public interface UpdateExecutor<U> {
    /**
     * Performs the update.
     * @param update The update to execute.
     * @return True if the update executed successfully, false if the update failed.
     * If this method returns false, then the {@link Updater} will halt
     * and return the version the system is at before this update executes.
     * 
     * @see Updater#executeUpdates(java.util.List, com.lagopusempire.basicupdater.UpdateExecutor) 
     * Updater.executeUpdates()
     * @see Updater#update(java.lang.Object, java.lang.Object, com.lagopusempire.basicupdater.UpdateExecutor)
     * Updater.update()
     */
    boolean doUpdate(U update);
}
