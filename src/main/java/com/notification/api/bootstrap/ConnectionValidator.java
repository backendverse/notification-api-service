package com.notification.api.bootstrap;

import com.mongodb.client.MongoClient;
import com.notification.api.config.ApplicationProperties;
import com.notification.api.exception.ValidationException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class ConnectionValidator {

    private final RedisConnectionFactory redisConnectionFactory;
    private final MongoClient mongoClient;
    private final ApplicationProperties properties;


    @PostConstruct
    public void init() {
        testRedisConnection();
        testMongoConnection();
        testKafkaConnection();
    }

    private void testKafkaConnection() {
        try (AdminClient client = AdminClient.create(Map.of("bootstrap.servers", properties.getBootstrapKafkaServers()))) {
            ListTopicsResult topics = client.listTopics();
            topics.names().get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Error while pinging kafka connection : ", e);
            throw new ValidationException("Error while pinging kafka connection", HttpStatus.BAD_REQUEST.value());
        }
    }

    private void testMongoConnection() {
        try {
            mongoClient.listDatabases().first();
        } catch (Exception e) {
            log.error("Error while pinging mongo connection");
            throw e;
        }
    }

    private void testRedisConnection() {
        try {
            redisConnectionFactory.getConnection().ping();
        } catch (Exception e) {
            log.error("Error while pinging redis connection");
            throw e;
        }
    }

}
