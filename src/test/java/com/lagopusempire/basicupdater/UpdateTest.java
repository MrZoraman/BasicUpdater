package com.lagopusempire.basicupdater;

import org.junit.Test;

/**
 *
 * @author Foomf
 */
public class UpdateTest {
    
    public UpdateTest() {
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullPrereq() {
        new Update<>(null, 0, true);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullOutput() {
        new Update<>(0, null, true);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullUpdate() {
        new Update<>(0, 1, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEqualPrereqOutput() {
        new Update<>(0, 0, true);
    }
}
