package com.lagopusempire.basicupdater;

import com.github.zafarkhaja.semver.Version;
import java.util.Optional;

/**
 *
 * @author Foomf
 */
public class Main implements TaskExecutor, Runnable {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
    
    @Override
    public void run() {
        Updater<Version> updater = new Updater(this, Version.valueOf("1.0.0"));
        updater.addUpdate(new Update(Version.valueOf("1.0.0"), Version.valueOf("1.0.1"), this, "first update"));
        updater.addUpdate(new Update(Version.valueOf("1.0.1"), Version.valueOf("1.0.2"), this, "second update"));
        updater.addUpdate(new Update(Version.valueOf("1.0.2"), Version.valueOf("1.0.3"), this, "second update"));
        try {
            Optional<Version> version = updater.DoUpdates(Version.valueOf("1.0.3"));
            System.out.println("Updated to version " + version);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean executeTask(String data) {
        System.out.println("Task executed!: " + data);
        return true;
    }
}
