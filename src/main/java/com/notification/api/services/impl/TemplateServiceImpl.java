package com.notification.api.services.impl;

import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.exception.ValidationException;
import com.notification.api.models.context.NotificationContext;
import com.notification.api.models.context.NotificationContextHolder;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.services.interfaces.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.notification.api.constants.ErrorConstants.TEMPLATE_ALREADY_EXISTS_ERROR;
import static com.notification.api.utils.CommonUtils.generateUUID;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateServiceImpl implements TemplateService {

    private final TemplateDao templateDao;

    @Override
    public TemplateResponse createTemplate(CreateTemplateRequest request) {
        NotificationContext context = NotificationContextHolder.getContext();

        templateDao.findByTenantIdAndName(context.tenantId(), request.getName()).ifPresent(template -> {
            throw new ValidationException(TEMPLATE_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST.value());
        });

        Template template = new Template();
        template.setId(generateUUID());
        template.setTenantId(UUID.fromString(context.tenantId()));
        BeanUtils.copyProperties(request, template);
        template.entityCreated();
        templateDao.save(template);
        return new TemplateResponse(template);
    }
}
