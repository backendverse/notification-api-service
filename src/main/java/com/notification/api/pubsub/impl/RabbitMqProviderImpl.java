package com.notification.api.pubsub.impl;

import com.notification.api.pubsub.interfaces.RabbitMqProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(value = "messaging.fallback.rabbitmq.enabled", havingValue = "true")
class RabbitMqProviderImpl implements RabbitMqProvider {

    @Override
    public boolean sendNotification(String topic, String message) {
        log.info("Sending Notification Using RabbitMq For Topic: {} For Message : {}", topic, message);
        return false;
    }
}
