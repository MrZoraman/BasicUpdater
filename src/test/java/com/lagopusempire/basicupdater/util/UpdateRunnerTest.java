package com.lagopusempire.basicupdater.util;

import com.lagopusempire.basicupdater.Update;
import com.lagopusempire.basicupdater.Updater;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Foomf
 */
public class UpdateRunnerTest {
    
    public UpdateRunnerTest() {
    }

    /**
     * Test of executeUpdates method, of class UpdateRunner.
     */
    @Test
    public void testExecuteUpdatesFail() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        updater.addUpdate(new Update<>(1, 2, 2)); // 1 -> 2, 1
        updater.addUpdate(new Update<>(2, 3, 3)); // 2 -> 3, 1
        updater.addUpdate(new Update<>(3, 4, 4)); // 3 -> 4, 1
        List<Update<Integer, Integer>> updates = updater.getUpdatesTo(0, 4);
        
        UpdateRunner<Integer, Integer> runner = new UpdateRunner<>(update -> update < 3);
        Optional<Integer> version = runner.executeUpdates(updates);
        assertTrue(version.isPresent());
        int v = version.get();
        assertEquals(2, v);
    }
    
    @Test
    public void testNoUpdatesYieldsEmpty() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        updater.addUpdate(new Update<>(1, 2, 2)); // 1 -> 2, 1
        updater.addUpdate(new Update<>(2, 3, 3)); // 2 -> 3, 1
        updater.addUpdate(new Update<>(3, 4, 4)); // 3 -> 4, 1
        List<Update<Integer, Integer>> updates = updater.getUpdatesTo(2, 2);
        
        UpdateRunner<Integer, Integer> runner = new UpdateRunner<>(update -> update < 3);
        Optional<Integer> version = runner.executeUpdates(updates);
        assertFalse(version.isPresent());
    }
    
    @Test
    public void testSuccessfulUpdate() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        updater.addUpdate(new Update<>(1, 2, 2)); // 1 -> 2, 1
        updater.addUpdate(new Update<>(2, 3, 3)); // 2 -> 3, 1
        updater.addUpdate(new Update<>(3, 4, 4)); // 3 -> 4, 1
        List<Update<Integer, Integer>> updates = updater.getUpdatesTo(0, 4);
        
        UpdateRunner<Integer, Integer> runner = new UpdateRunner<>(update -> true);
        Optional<Integer> version = runner.executeUpdates(updates);
        assertTrue(version.isPresent());
        int v = version.get();
        assertEquals(4, v);
    }
}
