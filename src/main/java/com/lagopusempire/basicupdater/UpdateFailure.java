/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lagopusempire.basicupdater;

/**
 *
 * @author Foomf
 */
public class UpdateFailure {
    private final FailReason reason;
    private final String additionalInfo;
    
    UpdateFailure(FailReason reason, String additionalInfo) {
        this.reason = reason;
        this.additionalInfo = additionalInfo;
    }
    
    public FailReason getReason() {
        return reason;
    }
    
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    
    @Override
    public String toString() {
        return reason + ": " + additionalInfo;
    }
}
