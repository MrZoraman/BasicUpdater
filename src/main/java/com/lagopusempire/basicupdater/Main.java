package com.lagopusempire.basicupdater;

import com.github.zafarkhaja.semver.Version;
import java.util.List;

/**
 *
 * @author Foomf
 */
public class Main implements Runnable {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    
    @Override
    public void run() {
        Updater<Version, Integer> updater = new Updater();
        updater.addUpdate(new Update(Version.valueOf("1.0.0"), Version.valueOf("1.0.1"), 1));
        updater.addUpdate(new Update(Version.valueOf("1.0.1"), Version.valueOf("1.0.2"), 2));
        updater.addUpdate(new Update(Version.valueOf("1.0.2"), Version.valueOf("1.0.3"), 3));
        List<Integer> updates = updater.getUpdatesTo(Version.valueOf("1.0.0"), Version.valueOf("1.0.3"));
        updates.forEach(System.out::println);
    }
}
