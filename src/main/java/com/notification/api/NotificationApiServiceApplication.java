package com.notification.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"com.notification.api", "com.common.sdk"})
@SpringBootApplication
public class NotificationApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApiServiceApplication.class, args);
    }

    void pending() {
        // Implement Strict Validation
        // 1. Input Dynamic Variables
        // 2. Input Template
        // 3. Input Ids
    }

    void agendaPart1() {
        // 1. packages
        // 2. Implement Abstract Generic Global Exception Handler
        // 3. Configure Notification Context To Propagate Common Data Using Thread Local
        // 4. Configure Auth Filter To Filter And Process Context Data
        // 5. Design Entity & Request/ Response
    }

    void agendaPart2() {
        // 5. Implement Service & Dao Layer
        // 6. Implement Create Template API
    }

    void agendaPart3() {
        // 5. Implement Generic Search API
        // 6. Implement Template Filter API
    }

    void agendaPart4() {
        // Implement Update Template API
        // Implement Delete Template API
    }

    void agendaPart5() {
        // Implement Send Notification API
        // If Validation Failed Send To Audit Topic
        // If Validation Passed Sent To Ingest Topic
    }

    void agendaPart6() {
        // Implement Generic Publisher, Which rely on Abstraction To For Publisher/Fallback
    }

    void agendaPart7() {
        // Design Generic Publisher With Dynamic Control & Using It In Our Code
        // Understanding Logging Pattern
    }

    void agendaPart8() {
        // Create Customized bean Of ObjectMapper
        // Create Common Methods Inside Generic Publisher To Avoid Extra Publishing Overhead
        // Trigger Kafka Send - Topic {notification.ingest}
        // Trigger Kafka Send - Topic {notification.audit} - Pending For Audit Implementation
    }

    void agendaPart9() {
        // Implementing Cache Using {Redis Hash} To Reduce DB Load
    }

    void agendaPart10() {
        // Implement Redis Configuration For Global TTL Expiry
        // Implement Redis Connection Validation At Application Startup
        // Implement Mongo Connection Validation At Application Startup
        // Implement Validation For Kafka At Application Startup
    }

    void bugFixingAndValidation() {

//        ---------Global Exception Handler---------
//        Null pointer when only constructing with string
//        ---------Global Exception Handler---------

//          ---------Filter Level---------
//          Send 403 when tenantId is missing
//          ---------Filter Level---------

//        ---------Create template API---------
//        CreateTemplateRequest,UpdateTemplateRequest -> templateVariables, messageTemplate - size
//        ---------Create template API---------

//        ---------Notification API---------
//        send notification API -  validate template variables
//        notification type - validate required field
//        ---------Notification API---------
    }

    void agendaPart11() {
        // Enable Global SDK
        // Remove MDC From Filter
        // Refactor Base Exception Handler
        // Refactor Controllers For Generic Response
        // Then we'll test the apiResponse Structure
    }

}
