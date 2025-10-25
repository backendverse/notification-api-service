package com.notification.api.services.impl;

import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.exception.ValidationException;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.IngestTopicDTO;
import com.notification.api.models.request.SendNotificationRequest;
import com.notification.api.services.interfaces.NotificationService;
import com.notification.api.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.notification.api.constants.ErrorConstants.TEMPLATE_NOT_EXISTS_WITH_ID_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final TemplateDao templateDao;

    @Override
    public void sendNotification(final SendNotificationRequest request) {
        Optional<Template> byTenantIdAndId = templateDao.findByTenantIdAndId(CommonUtils.getCurrentTenantId(), request.getTemplateId());
        if (byTenantIdAndId.isEmpty()) {
            // TODO send to audit topic;
            throw new ValidationException(TEMPLATE_NOT_EXISTS_WITH_ID_ERROR, HttpStatus.BAD_REQUEST.value());
        }

        IngestTopicDTO ingestTopicDTO = new IngestTopicDTO();
        ingestTopicDTO.setRequestId(CommonUtils.getCurrentTraceId());
        ingestTopicDTO.setTenantId(CommonUtils.getCurrentTenantId());
        ingestTopicDTO.setReceivedAt(CommonUtils.getCurrentTimeStamp());
        ingestTopicDTO.setTemplateId(request.getTemplateId());
        ingestTopicDTO.setDynamicVariables(request.getDynamicVariables());
        ingestTopicDTO.setNotificationType(request.getNotificationType());

        // TODO publish to Ingest Topic
    }

}
