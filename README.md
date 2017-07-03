# BasicUpdater
This library contains some basic logic for resolving which updates need to be run. You give the library a list of updates, the current version of whatever needs updating, and the version you want to update to. BasicUpdater then figures out what updates need to be run. It can even run the updates for you if you tell it how.

The BasicUpdater makes heavy use of generics, so it maintains type safety while being able to be completely decoupled from any system that uses it.

# Maven
todo

# Javadoc
todo

# Tutorial

This will walk you through all of the features BasicUpdater has to offer. I've tested everything in this section, so if something does not work for you, please let me know or create an issue.

This tutorial assumes you have knowledge of java 8 and functional interfaces.

The first thing you will need to know is your types. What types will your versions be, and what type will the updates be? Your version type is probably going to be an Integer or a SemVer. However, it can be anything with a working .equals() method. Your Update type can be anything you want. The BasicUpdater doesn't care what type it is, as it basically acts as a container. When it is time for the updates to run, it will give the updates for you to execute, as you know what to call, etc. Hopefully it will be more clear as I begin to show you examples.

For this first example, we'll use ints for our version, and strings for our update-specific data.

```java
Updater<Integer, String> updater = new Updater<>();
```

The updater has now been created. It must be given objects of type Update. This is a class that stores all the info about an update (what version comes before it, what version it updates to, and the update itself). It should be easy to write parsers that spit out a collection of Update objects. We can add them to the updater like so:

```java
Updater<Integer, Character> updater = new Updater<>();
updater.addUpdate(new Update<>(1, 2, "first update"));
updater.addUpdate(new Update<>(2, 3, "second update"));
updater.addUpdate(new Update<>(3, 4, "third update"));
```

This adds three updates. The first update goes from version 1 to version 2. The second udpate goes from version 2 to version 3. The third update goes from version 3 to version 4.

Note that the updates can be added in any order. The updater does not care what order they are added in. They are all added to hash map anyways. What this also means is that you cannot have duplicate updates. For example:

```
updater.addUpdate(new Update<>(1, 2, "first update"));
updater.addUpdate(new Update<>(1, 2, "second update"));
```

will throw a DuplicateUpdateException. This is a runtime exception, so you will need to check for it yourself. If you are confident that your updates make sense, you will be fine. If you have two updates that take place "at the same time" after a version, that is probably a code/configuration smell. BasicUpdater does not support parallel updating.

Once the updates have been added, you can ask the updater for a list of updates that must be run by giving it a start version and an end version. Because our version type is Integer, the two parameters are of type Integer. Let's pretend our system is already at version 2.

```java
Updater<Integer, String> updater = new Updater<>();
updater.addUpdate(new Update<>(1, 2, "first update"));
updater.addUpdate(new Update<>(2, 3, "second update"));
updater.addUpdate(new Update<>(3, 4, "third update"));
List<Update<Integer, String>> updates = updater.getUpdatesTo(2, 4);
```

getUpdatesTo() will never return null. If there are no updates to be done, it will return an empty list. From here, you are free to run your updates.

```java
Updater<Integer, String> updater = new Updater<>();
updater.addUpdate(new Update<>(1, 2, "first update"));
updater.addUpdate(new Update<>(2, 3, "second update"));
updater.addUpdate(new Update<>(3, 4, "third update"));
List<Update<Integer, String>> updates = updater.getUpdatesTo(1, 4);
updates.stream().map(update -> update.getUpdate()).forEach(System.out::println);
```

Remember, an Update contains all the info about the update, including the update itself. We use the map() function to get a stream of updates, as that is the only thing we care about for now. The output will be as follows:

```
second update
third update
```

However, updates can fail. It might be better if we pay attention to what updates succeed and which ones fail.

```java
Updater<Integer, String> updater = new Updater<>();
updater.addUpdate(new Update<>(1, 2, "first update"));
updater.addUpdate(new Update<>(2, 3, "second update"));
updater.addUpdate(new Update<>(3, 4, "third update"));
int currentVersion = 2;
List<Update<Integer, String>> updates = updater.getUpdatesTo(currentVersion, 4);
for(Update<Integer, String> update : updates) {
    String updateData = update.getUpdate();
    //your specific code for knowing what to do with an update
    System.out.println(updateData);
    //
    currentVersion = update.getTo();
}
System.out.println("Current version: " + currentVersion);
```

Now we know how far we got in the updates. Now if your update specific code fails for any reason, you can simply ```break;``` out of the loop, and you'll know where you need to start next time you do updates (hopefully they'll be successful next time...).

This is a very common bit of code to write though. Thus, it has been added to the updater as a method. This also introduces the concept of an UpdateExecutor. This is a functional interface that takes update data and returns true if the update succeeded and false if the update failed.

```java
Updater<Integer, String> updater = new Updater<>();
updater.addUpdate(new Update<>(1, 2, "first update"));
updater.addUpdate(new Update<>(2, 3, "second update"));
updater.addUpdate(new Update<>(3, 4, "third update"));
List<Update<Integer, String>> updates = updater.getUpdatesTo(2, 4);
Optional<Integer> version = updater.executeUpdates(updates, updateData -> {
    System.out.println(updateData);
    return true;
});
System.out.println("Current version: " + version.get());
```

This produces the same result as above. In this case all of our updates are guranteed to succeed. Your update logic will probably be more complex. The caveat with using the UpdateExecutor interface is that you cannot throw exceptions. If you want more robust exception handling, you will want to stick with the foreach loop.

It's sort of bulky to get a list just to pass it back into the updater. Therefore, there is a method that wraps all of this up in one nice package. This is probably the method you will be using the most.

```java
Updater<Integer, String> updater = new Updater<>();
updater.addUpdate(new Update<>(1, 2, "first update"));
updater.addUpdate(new Update<>(2, 3, "second update"));
updater.addUpdate(new Update<>(3, 4, "third update"));
Optional<Integer> version = updater.update(2, 4, updateData -> {
    System.out.println(updateData);
    return true;
});
System.out.println("Current version: " + version.get());
```

While I am not doing it in these examples, it is important to check that the optional is not empty. If the optional is empty, that means that there were no updates to run (you asked the updater to go from version 2 to version 2 or something...).
