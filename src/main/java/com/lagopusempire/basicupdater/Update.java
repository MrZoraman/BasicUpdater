package com.lagopusempire.basicupdater;

/**
 *
 * @author Foomf
 */
public class Update<V, U> {
    
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
        
        if(update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }
        
        if(preReq.equals(output)) {
            throw new IllegalArgumentException(
                    "Prerequisite version cannot be the same as the result version.");
        }
        
        this.preReq = preReq;
        this.output = output;
        this.update = update;
    }
    
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
