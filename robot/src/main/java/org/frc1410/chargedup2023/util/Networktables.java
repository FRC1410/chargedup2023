package org.frc1410.chargedup2023.util;

import edu.wpi.first.networktables.*;

public interface Networktables {
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
        return table.getDoubleTopic(topic.getName()).subscribe(0.0);
    }
    static DoubleSubscriber SubscriberFactory(NetworkTable table, DoubleTopic topic, double defaultValue) {
        return table.getDoubleTopic(topic.getName()).subscribe(defaultValue);
    }
}
