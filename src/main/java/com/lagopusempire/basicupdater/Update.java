package com.lagopusempire.basicupdater;

/**
 *
 * @author Foomf
 */
public class Update<V, U> {
    
    private final V from;
    private final V to;
    
    private final U update;
    
    public Update(V from, V to, U update) {
        if(from == null) {
            throw new IllegalArgumentException("Prerequisite version cannot be null.");
        }
        
        if(to == null) {
            throw new IllegalArgumentException("Output version cannot be null.");
        }
        
        if(update == null) {
            throw new IllegalArgumentException("Update cannot be null.");
        }
        
        if(from.equals(to)) {
            throw new IllegalArgumentException(
                    "Prerequisite version cannot be the same as the result version.");
        }
        
        this.from = from;
        this.to = to;
        this.update = update;
    }
    
    public V getFrom() {
        return from;
    }
    
    public V getTo() {
        return to;
    }
    
    public U getUpdate() {
        return update;
    }
}
