package com.notification.api.pubsub.publisher;

public interface GenericPublisher {

    void sendDataToIngest(Object input);

    void sendDataToAudit(Object input);

    void sendNotification(String topic, String message);
}
