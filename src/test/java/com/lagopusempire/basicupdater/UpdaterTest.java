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
        updater.addUpdate(new Update<>(1, 2, 2)); // 1 -> 2, 1
        updater.addUpdate(new Update<>(2, 3, 3)); // 2 -> 3, 1
        updater.addUpdate(new Update<>(3, 4, 4)); // 3 -> 4, 1
        List<Update<Integer, Integer>> updates = updater.getUpdatesTo(0, 4);
        int total = 0;
        total = updates.stream().map(u -> u.getUpdate()).reduce(total, Integer::sum);
        assertEquals(10, total);
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
    public void testUpdateMissingException() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        //updater.addUpdate(new Update<>(1, 2, 1)); // 1 -> 2, 1 (missing)
        updater.addUpdate(new Update<>(2, 3, 1)); // 2 -> 3, 1
        updater.addUpdate(new Update<>(3, 4, 1)); // 3 -> 4, 1
        try {
            updater.getUpdatesTo(0, 4);
        } catch (UpdateMissingException expected) {
            assertEquals(1, expected.getMissingVersion());
        }
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
        List<Update<Character, Integer>> updates = updater.getUpdatesTo('A', 'A');
        assertTrue(updates.isEmpty());
    }
    
    @Test
    public void testOrder() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        updater.addUpdate(new Update<>(1, 2, 2)); // 1 -> 2, 2
        updater.addUpdate(new Update<>(2, 3, 3)); // 2 -> 3, 3
        updater.addUpdate(new Update<>(3, 4, 4)); // 3 -> 4, 4
        List<Update<Integer, Integer>> updateList = updater.getUpdatesTo(0, 4);
        Object[] updates = updateList.stream().map(p -> p.getUpdate()).toArray();
        Integer[] expected = new Integer[] {1, 2, 3, 4};
        Assert.assertArrayEquals(expected, updates);
    }
    
    @Test
    public void testCircularUpdateException() {
        Updater<Character, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>('A', 'B', 1)); // A -> B, 1
        updater.addUpdate(new Update<>('B', 'C', 2)); // B -> C, 2
        updater.addUpdate(new Update<>('C', 'B', 3)); // C -> B, 3
        updater.addUpdate(new Update<>('D', 'A', 4)); // D -> A, 4
        try {
            updater.getUpdatesTo('A', 'D');
        } catch (CircularUpdateException expected) {
            assertEquals('C', expected.getCircularExceptionCause().getFrom());
            assertEquals('B', expected.getCircularExceptionCause().getTo());
        }
    }
    
    @Test
    public void testUpdateVersionsCorrect() {
        Updater<Integer, Integer> updater = new Updater<>();
        updater.addUpdate(new Update<>(0, 1, 1)); // 0 -> 1, 1
        updater.addUpdate(new Update<>(1, 2, 2)); // 1 -> 2, 2
        updater.addUpdate(new Update<>(2, 3, 3)); // 2 -> 3, 3
        updater.addUpdate(new Update<>(3, 4, 4)); // 3 -> 4, 4
        List<Update<Integer, Integer>> updateList = updater.getUpdatesTo(0, 4);
        Object[] updates = updateList.stream().map(p -> p.getFrom()).toArray();
        Integer[] expected = new Integer[] {0, 1, 2, 3};
        Assert.assertArrayEquals(expected, updates);
    }
}
