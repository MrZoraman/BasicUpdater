package com.lagopusempire.basicupdater;

import java.util.Optional;

/**
 *
 * @author Foomf
 */
public class Update<V, U> {
    //private final TaskExecutor executor;
    
    private final V preReq;
    private final V output;
    
    private final U update;
    
    public Update(V preReq, V output, U update) {
        if(preReq == null) {
            throw new IllegalArgumentException("Prerequisite version cannot be null.");
        }
        
        if(output == null) {
            throw new IllegalArgumentException("Output version cannot be null.");
        }
        
//        if(executor == null) {
//            throw new IllegalArgumentException("Task executor cannot be null.");
//        }
        
        if(update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }
        
        this.preReq = preReq;
        this.output = output;
        //this.executor = executor;
        this.update = update;
    }
    
//    /**
//     * Takes the current version of the system and performs an update.
//     * @param currentVersion The current version of the system
//     * @return The next version of the system, or Optional.empty() if the update failed.
//     */
//    public Optional<V> doUpdate(V currentVersion) {
//        if(currentVersion == null) {
//            throw new IllegalArgumentException("Current version cannot be null.");
//        }
//        
//        if(!currentVersion.equals(preReq)) {
//            throw new IllegalStateException("error!");
//            //TODO: this
//            //throw new Exception("uh oh!");
//            //return Optional.empty();
//        }
//        
//        if(!executor.executeTask(data)) {
//            return Optional.empty();
//        }
//        
//        return Optional.of(output);
//    }
    
    V getPreReq() {
        return preReq;
    }
    
    V getOutput() {
        return output;
    }
    
    U getUpdate() {
        return update;
    }
}
