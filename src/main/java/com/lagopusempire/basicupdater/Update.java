package com.lagopusempire.basicupdater;

import com.github.zafarkhaja.semver.Version;

/**
 *
 * @author Foomf
 */
public class Update {
    private final TaskExecutor executor;
    
    private final Version preReq;
    private final Version output;
    
    private final String data;
    
    public Update(Version preReq, Version output, TaskExecutor executor, String data) {
        if(preReq == null) {
            throw new IllegalArgumentException("Prerequisite version cannot be null.");
        }
        
        if(output == null) {
            throw new IllegalArgumentException("Output version cannot be null.");
        }
        
        if(executor == null) {
            throw new IllegalArgumentException("Task executor cannot be null.");
        }
        
        if(data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        
        this.preReq = preReq;
        this.output = output;
        this.executor = executor;
        this.data = data;
    }
    
    public Version doUpdate(Version currentVersion) throws Exception {
        if(currentVersion == null) {
            throw new IllegalArgumentException("Current version cannot be null.");
        }
        
        if(!currentVersion.equals(preReq)) {
            //TODO: this
            throw new Exception("uh oh!");
        }
        
        executor.executeTask(data);
        
        return output;
    }
    
    Version getPreReq() {
        return preReq;
    }
}
