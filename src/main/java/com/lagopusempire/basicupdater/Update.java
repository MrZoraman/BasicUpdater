package com.lagopusempire.basicupdater;

/**
 *
 * @author Foomf
 */
public class Update<V> {
    private final TaskExecutor executor;
    
    private final V preReq;
    private final V output;
    
    private final String data;
    
    public Update(V preReq, V output, TaskExecutor executor, String data) {
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
    
    public V doUpdate(V currentVersion) throws Exception {
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
    
    V getPreReq() {
        return preReq;
    }
}
