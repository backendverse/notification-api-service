package com.notification.api.pubsub.fallback;

public interface GenericFallbackPublisher {

    boolean sendNotification(String topic, String message);
}
