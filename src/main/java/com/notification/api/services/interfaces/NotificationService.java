package com.notification.api.services.interfaces;

import com.notification.api.models.request.SendNotificationRequest;

public interface NotificationService {
    void sendNotification(SendNotificationRequest request);
}
