package com.notification.api.pubsub.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.api.config.ApplicationProperties;
import com.notification.api.exception.ValidationException;
import com.notification.api.pubsub.fallback.GenericFallbackPublisher;
import com.notification.api.pubsub.primary.GenericProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
class GenericPublisherImpl implements GenericPublisher {

    private final List<GenericProvider> genericPublishers;
    private final List<GenericFallbackPublisher> genericFallbackPublishers;
    private ObjectMapper mapper;
    private ApplicationProperties properties;

    @Override
    public void sendDataToIngest(final Object input) {
        sendNotification(properties.getIngestTopic(), convertDataIntoString(input));
    }

    @Override
    public void sendDataToAudit(final Object input) {
        sendNotification(properties.getAuditTopic(), convertDataIntoString(input));
    }


    @Override
    public void sendNotification(final String topic, final String message) {
        log.info("sending notification using generic publisher");

        AtomicBoolean success = new AtomicBoolean(false);

        genericPublishers.forEach(publisher -> {
            boolean publisherStatus = publisher.sendNotification(topic, message);
            if (!success.get()) {
                success.set(publisherStatus);
            }
            if (publisherStatus) {
                log.info("notification send to topic : {} using provider : {}",
                        topic, publisher.getClass().getSimpleName());
            } else {
                log.error("Error while publishing data for topic : {} using provider :{}",
                        topic, publisher.getClass().getSimpleName());
            }
        });

        genericFallbackPublishers.forEach(fallback -> {
            if (!success.get()) {
                boolean publisherStatus = fallback.sendNotification(topic, message);
                if (publisherStatus) {
                    success.set(true);
                    log.info("notification send to topic : {} using fallback provider : {}",
                            topic, fallback.getClass().getSimpleName());
                } else {
                    log.error("Error while publishing data for topic : {} using fallback provider :{}",
                            topic, fallback.getClass().getSimpleName());
                }
            }
        });

    }

    private String convertDataIntoString(final Object input) {
        try {
            return mapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            throw new ValidationException("Error while parsing input payload",
                    HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }


}
