package com.notification.api.pubsub.impl;

import com.notification.api.pubsub.interfaces.AwsSqsProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(value = "messaging.providers.aws.enabled", havingValue = "true")
class AwsSQSProviderImpl implements AwsSqsProvider {
    @Override
    public boolean sendNotification(String topic, String message) {
        log.info("Sending Notificaiton using AWS SQS Provider for topic : {}", topic);
        return false;
    }
}
