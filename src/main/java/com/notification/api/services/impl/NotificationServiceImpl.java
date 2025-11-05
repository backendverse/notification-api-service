package com.notification.api.services.impl;

import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.exception.ValidationException;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.IngestTopicDTO;
import com.notification.api.models.request.SendNotificationRequest;
import com.notification.api.pubsub.publisher.GenericPublisher;
import com.notification.api.services.interfaces.NotificationService;
import com.notification.api.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.notification.api.constants.ErrorConstants.TEMPLATE_NOT_EXISTS_WITH_ID_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final TemplateDao templateDao;
    private final GenericPublisher genericPublisher;

    @Override
    public void sendNotification(final SendNotificationRequest request) {
        Optional<Template> byTenantIdAndId = templateDao.findByTenantIdAndId(CommonUtils.getCurrentTenantId(), request.getTemplateId());
        if (byTenantIdAndId.isEmpty()) {
            // TODO send to audit topic;
//            genericPublisher.sendDataToAudit();
            throw new ValidationException(TEMPLATE_NOT_EXISTS_WITH_ID_ERROR, HttpStatus.BAD_REQUEST.value());
        }

        byTenantIdAndId.ifPresent(template -> {
            Map<String, Object> requestDynamicVariables = request.getDynamicVariables();
            if (template.getTemplateVariables().size() != requestDynamicVariables.size()
                    || template.getTemplateVariables().values().stream()
                    .anyMatch(variable -> !requestDynamicVariables.containsKey(variable))) {
                throw new ValidationException("Invalid Dynamic Variables");
            }
        });

        IngestTopicDTO ingestTopicDTO = new IngestTopicDTO();
        ingestTopicDTO.setRequestId(CommonUtils.getCurrentTraceId());
        ingestTopicDTO.setTenantId(CommonUtils.getCurrentTenantId());
        ingestTopicDTO.setReceivedAt(CommonUtils.getCurrentTimeStamp());
        ingestTopicDTO.setDynamicVariables(request.getDynamicVariables());
        ingestTopicDTO.setTemplateId(request.getTemplateId());
        ingestTopicDTO.setNotificationType(request.getNotificationType());


        genericPublisher.sendDataToIngest(ingestTopicDTO);

    }

}
