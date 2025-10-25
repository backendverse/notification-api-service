package com.notification.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
