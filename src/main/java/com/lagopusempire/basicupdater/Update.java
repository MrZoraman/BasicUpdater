package com.lagopusempire.basicupdater;

/**
 *
 * @author Foomf
 */
public class Update<T> {
    private final TaskExecutor executor;
    
    private final T preReq;
    private final T output;
    
    private final String data;
    
    public Update(T preReq, T output, TaskExecutor executor, String data) {
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
    
    public T doUpdate(T currentVersion) throws Exception {
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
    
    T getPreReq() {
        return preReq;
    }
}
