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
@FunctionalInterface
public interface UpdateExecutor<U> {
    boolean doUpdate(U update);
}