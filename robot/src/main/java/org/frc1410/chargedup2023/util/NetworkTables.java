package org.frc1410.chargedup2023.util;

import edu.wpi.first.networktables.*;

public interface NetworkTables {
    /**
     * Creates a publisher for a topic in a given table with a starting value
     * @param table NetworkTable, desired table for Publisher
     * @param name String, desired name for topic
     * @param startingValue double, desired starting value
     * @return DoublePublisher
     */
    static DoublePublisher PublisherFactory(NetworkTable table, String name, double startingValue) {
        DoublePublisher publisher = table.getDoubleTopic(name).publish();
        publisher.set(startingValue);
        publisher.setDefault(startingValue);
        return publisher;
    }
    
    static DoublePublisher PublisherFactory(NetworkTable table, String name, double startingValue, double defaultValue) {
        DoublePublisher publisher = table.getDoubleTopic(name).publish();
        publisher.set(startingValue);
        publisher.setDefault(defaultValue);
        return publisher;
    }

    /**
     * Creates a subscriber for a topic from a table
     * @param table Networktable, desired table for subscriber
     * @param topic DoubleTopic, desired topic for subscriber
     * @return DoubleSubscriber
     */
    static DoubleSubscriber SubscriberFactory(NetworkTable table, DoubleTopic topic) {
        String name = topic.getName().substring(topic.getName().lastIndexOf("/") + 1);
        return table.getDoubleTopic(name).subscribe(0.0);
    }
    
    static DoubleSubscriber SubscriberFactory(NetworkTable table, DoubleTopic topic, double defaultValue) {
        String name = topic.getName().substring(topic.getName().lastIndexOf("/") + 1);
        return table.getDoubleTopic(name).subscribe(defaultValue);
    }

    /* Setup for any class requiring network tables
    NetworkTableInstance instance = NetworkTableInstance.getDefault();
    NetworkTable table = instance.getTable("Test");

    DoublePublisher pub = Networktables.PublisherFactory(table, "Testing", 0);
    DoubleSubscriber sub = Networktables.SubscriberFactory(table, pub.getTopic(), 0);
     */
}