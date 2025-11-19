# 🚀 Notification API Service

Welcome to **[Backend Verse](https://www.youtube.com/@BackendVerse)**! 🎥

### 📦 Part of: *[Scalable Notification System using Kafka](https://www.youtube.com/playlist?list=PLdUn31k8Q723lAfT2QOWoSA4C-UFQLDGI)*

**Tech Stack:** Spring Boot | Apache Kafka | Redis | Docker

---

## 🧭 Overview

The **Notification API Service** is the **entry point** of the Scalable Notification System.
It handles incoming notification requests, validates payloads, and publishes events into the Kafka pipeline for further processing by downstream services (e.g., Notification Processor, Channel Workers).

This service ensures **data integrity, lightweight validation, and clean event publishing** to maintain high throughput and low latency.

---

## ⚙️ Responsibilities

✅ **Receive Notification Requests**
Handles incoming API calls like:

```
POST /api/notify
```

✅ **Validate Payloads**
Checks fields such as:

* `userEmail`
* `templateId`
* `metadata`
  If invalid → pushes audit entry to `notification.audit` (status = `VALIDATION_FAILED`).

✅ **Publish to Kafka**
If valid → publishes events to:

```
notification.ingest
```

✅ **Audit on Failure**
Invalid or rejected requests are sent to:

```
notification.audit
```

for tracking and observability.

---

## 🧠 Design Tips

💡 **Keep validation lightweight**
Avoid heavy lookups or enrichment at this layer.
Use caching (e.g. Redis) for metadata or template reference lookups to reduce DB hits.

💡 **Follow Clean Architecture**

* Keep business logic decoupled from controllers
* Use services for validation and message publishing
* Handle all exceptions via a centralized global exception handler

💡 **Prepare for Scalability**
This service is stateless and can be easily scaled horizontally behind a load balancer.

---

## 🏗️ High-Level Flow

```
Client
   ↓
Notification API (/api/notify)
   ↓
Validate Request
   ↓
 ┌──────────────────────────────┐
 │ Valid Request → Kafka Topic  │  → notification.ingest
 │ Invalid Request → Audit Log  │  → notification.audit
 └──────────────────────────────┘
```

---

## 🧩 Kafka Topics Used

| Topic Name            | Purpose                                          |
| --------------------- | ------------------------------------------------ |
| `notification.ingest` | Holds valid notification requests for processing |
| `notification.audit`  | Stores audit logs for invalid or failed requests |

---

## 🛠️ Key Components

| Component                             | Description                                                    |
| ------------------------------------- | -------------------------------------------------------------- |
| **GlobalExceptionHandler**            | Centralized exception management for the service               |
| **NotificationContext (ThreadLocal)** | Stores common request metadata across layers                   |
| **Auth Filter**                       | Filters and injects user/context data into NotificationContext |
| **Template Entity & DTOs**            | Defines structure for templates and notification payloads      |
| **KafkaProducerService**              | Publishes messages to `notification.ingest` topic              |

---

## 🧱 Package Structure (Suggested)

```
com.backendverse.notificationapi
│
├── config/
│   ├── KafkaProducerConfig.java
│   └── ThreadLocalContextConfig.java
│
├── controller/
│   └── NotificationController.java
│
├── service/
│   └── NotificationService.java
│
├── model/
│   ├── TemplateEntity.java
│   ├── NotificationRequest.java
│   └── NotificationResponse.java
│
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── CustomExceptions.java
│
└── filter/
    └── AuthFilter.java
```

---

## 📊 Future Enhancements

* Add authentication & request signature validation
* Integrate Redis caching for metadata lookups
* Add request tracing (Zipkin / OpenTelemetry)
* Rate-limiting & request deduplication

---

## 🧩 Related Services

| Service                                                                                              | Role                                                                  |
|------------------------------------------------------------------------------------------------------| --------------------------------------------------------------------- |
| **[Notification Processor Service](https://github.com/backendverse/notification-processor-service)** | Consumes from `notification.ingest`, enriches, and routes to channels |
| **Channel Worker Services (Email/SMS/Webhook)**                                                      | Delivers messages to external APIs                                    |
| **Audit Service**                                                                                    | Persists all notification audit logs for tracking                     |

---
## 🧪 Import Postman Collection

I have already included the Postman Collection file in this repo:
**`Scalable Notification Service.postman_collection.json`**

Follow these steps:

1. Open **Postman**.
2. Click on **Import** (top-left corner).
3. Select the file → `Scalable Notification Service.postman_collection.json`.
4. Done ✅ Now you can directly test APIs without writing cURL.

---

## 🧑‍💻 Author

🎥 YouTube: [BackendVerse](https://www.youtube.com/@backendverse)   
💬 Learn to build real-world backend systems with Spring Boot, Kafka & System Design (in Hindi)

---