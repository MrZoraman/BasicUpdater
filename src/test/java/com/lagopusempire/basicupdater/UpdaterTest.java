package com.lagopusempire.basicupdater;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Foomf
 */
public class UpdaterTest {
    
    public UpdaterTest() {
    }

    @Test
    public void testGetUpdatesTo() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        updater.addUpdate(new Update<>(1, 2, 1)); // 1 -> 2, 1
        updater.addUpdate(new Update<>(2, 3, 1)); // 2 -> 3, 1
        updater.addUpdate(new Update<>(3, 4, 1)); // 3 -> 4, 1
        List<Integer> updates = updater.getUpdatesTo(0, 4);
        int total = 0;
        total = updates.stream().reduce(total, Integer::sum);
        assertEquals(4, total);
    }
    
    @Test(expected = UpdateMissingException.class)
    public void testMissingUpdate() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        updater.addUpdate(new Update<>(1, 2, 1)); // 1 -> 2, 1
        //updater.addUpdate(new Update<>(2, 3, 1));  2 -> 3, 1 (missing)
        updater.addUpdate(new Update<>(3, 4, 1)); // 3 -> 4, 1
        updater.getUpdatesTo(0, 4);
    }
    
    @Test
    public void testSuccessYieldsEmptyError() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        updater.addUpdate(new Update<>(1, 2, 1)); // 1 -> 2, 1
        updater.addUpdate(new Update<>(2, 3, 1)); // 2 -> 3, 1
        updater.addUpdate(new Update<>(3, 4, 1)); // 3 -> 4, 1
        updater.getUpdatesTo(0, 4);
        
        assertFalse(updater.getMissingVersion().isPresent());
    }
    
    @Test
    public void testFailureYieldsError() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        //updater.addUpdate(new Update<>(1, 2, 1)); // 1 -> 2, 1 (missing)
        updater.addUpdate(new Update<>(2, 3, 1)); // 2 -> 3, 1
        updater.addUpdate(new Update<>(3, 4, 1)); // 3 -> 4, 1
        try {
            updater.getUpdatesTo(0, 4);
        } catch (UpdateMissingException ignored) {
            //expected
        }
        
        assertTrue(updater.getMissingVersion().isPresent());
        
        assertEquals(1, (int) updater.getMissingVersion().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullUpdate() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(null);
    }
    
    @Test(expected = CircularUpdateException.class)
    public void testLoop() {
        Updater<Character, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>('A', 'B', 1)); // A -> B, 1
        updater.addUpdate(new Update<>('B', 'C', 2)); // B -> C, 2
        updater.addUpdate(new Update<>('C', 'B', 3)); // C -> B, 3
        updater.addUpdate(new Update<>('D', 'A', 4)); // D -> A, 4
        updater.getUpdatesTo('A', 'D');
    }
    
    @Test
    public void testNoHarmLoop() {
        Updater<Character, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>('A', 'B', 1)); // A -> B, 1
        updater.addUpdate(new Update<>('B', 'C', 2)); // B -> C, 2
        updater.addUpdate(new Update<>('C', 'D', 3)); // C -> B, 3
        updater.addUpdate(new Update<>('D', 'A', 4)); // D -> A, 4
        updater.getUpdatesTo('A', 'D');
    }
    
    @Test
    public void testNoUpdates() {
        Updater<Character, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>('A', 'B', 1)); // A -> B, 1
        updater.addUpdate(new Update<>('B', 'C', 2)); // B -> C, 2
        updater.addUpdate(new Update<>('C', 'D', 3)); // C -> B, 3
        updater.addUpdate(new Update<>('D', 'A', 4)); // D -> A, 4
        List<Integer> updates = updater.getUpdatesTo('A', 'A');
        assertTrue(updates.isEmpty());
    }
    
    @Test
    public void testOrder() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        updater.addUpdate(new Update<>(1, 2, 2)); // 1 -> 2, 2
        updater.addUpdate(new Update<>(2, 3, 3)); // 2 -> 3, 3
        updater.addUpdate(new Update<>(3, 4, 4)); // 3 -> 4, 4
        List<Integer> updateList = updater.getUpdatesTo(0, 4);
        Integer[] updates = updateList.toArray(new Integer[updateList.size()]);
        Integer[] expected = new Integer[] {1, 2, 3, 4};
        Assert.assertArrayEquals(expected, updates);
    }
}
