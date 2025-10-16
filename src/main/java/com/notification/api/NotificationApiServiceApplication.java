package com.notification.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApiServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationApiServiceApplication.class, args);
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
}
