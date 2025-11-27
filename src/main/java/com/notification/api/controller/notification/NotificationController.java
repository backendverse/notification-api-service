package com.notification.api.controller.notification;

import com.common.sdk.models.interfaces.GenericAPIResponse;
import com.common.sdk.services.ResponseHandler;
import com.notification.api.models.request.SendNotificationRequest;
import com.notification.api.services.interfaces.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ResponseHandler responseHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public GenericAPIResponse<String> sendNotification(@Valid @RequestBody SendNotificationRequest notificationRequest) {
        notificationService.sendNotification(notificationRequest);
        return responseHandler.ok("Notification Sent Successfully");
    }

}
