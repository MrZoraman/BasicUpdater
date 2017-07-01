/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lagopusempire.basicupdater;

import org.junit.Test;
import static org.junit.Assert.*;

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
        new Update<>(0, 0, null);
    }
}
