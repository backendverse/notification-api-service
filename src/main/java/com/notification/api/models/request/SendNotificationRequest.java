package com.notification.api.models.request;

import com.notification.api.exception.ValidationException;
import com.notification.api.utils.CommonUtils;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

import static com.notification.api.constants.ErrorConstants.*;

@Data
public class SendNotificationRequest {

    @NotBlank(message = TEMPLATE_ID_IS_REQUIRED)
    private String templateId;
    private Map<String, Object> dynamicVariables;
    private NotificationType notificationType;

    @AssertTrue
    public boolean validateNotificationType() {
        if (CommonUtils.isNotEmpty(notificationType)) {
            throw new ValidationException(NOTIFICATION_TYPE_VARIABLE_ERROR);
        }

        if (CommonUtils.isNotEmpty(dynamicVariables)) {
            throw new ValidationException(SEND_NOTIFICATION_TEMPLATE_VARIABLE_ERROR);
        }
        return true;
    }

}
