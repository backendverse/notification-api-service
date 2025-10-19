package com.notification.api.services.impl;

import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.exception.ValidationException;
import com.notification.api.models.context.NotificationContextHolder;
import com.notification.api.models.entity.Template;
import com.notification.api.models.request.CreateUpdateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.UpdateTemplateRequest;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.models.response.TemplateResponseDTO;
import com.notification.api.services.interfaces.TemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.notification.api.constants.ErrorConstants.TEMPLATE_ALREADY_EXISTS_ERROR;
import static com.notification.api.constants.ErrorConstants.TEMPLATE_NOT_EXISTS_WITH_ID_ERROR;
import static com.notification.api.utils.CommonUtils.*;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateServiceImpl implements TemplateService {

    private final TemplateDao templateDao;

    @Override
    public TemplateResponse createTemplate(CreateUpdateTemplateRequest request) {
//        NotificationContext context = NotificationContextHolder.getContext();

        templateDao.findByTenantIdAndName(getCurrentTenantId(), request.getName()).ifPresent(template -> {
            throw new ValidationException(TEMPLATE_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST.value());
        });

        Template template = new Template();
        template.setId(generateUUID());
        template.setTenantId(getCurrentTenantId());
        BeanUtils.copyProperties(request, template);
        template.entityCreated();
        templateDao.save(template);
        return new TemplateResponse(template);
    }

    @Override
    public FilterTemplateResponse filterTemplate(TemplateFilterRequest request) {
        NotificationContextHolder.ignoreTenantIdInjection();
        Page<Template> templates = templateDao.filterTemplate(request.buildSearch(), request.buildPageRequest());
        List<TemplateResponseDTO> data = templates.stream().map(TemplateResponseDTO::new).toList();
        return new FilterTemplateResponse(data, templates.hasNext(), templates.getTotalElements());
    }

    @Override
    public TemplateResponse updateTemplate(String id, UpdateTemplateRequest request) {
        Template template = getTemplateForCurrentTenant(id);
        if (isNotEmpty(request.getName())) {
            templateDao.findByTenantIdAndName(getCurrentTenantId(), request.getName()).ifPresent(t -> {
                throw new ValidationException(TEMPLATE_ALREADY_EXISTS_ERROR, HttpStatus.BAD_REQUEST.value());
            });
            template.setName(request.getName());
        }
        if (isNotEmpty(request.getMessageTemplate())) {
            template.setMessageTemplate(request.getMessageTemplate());
        }
        if (isNotEmpty(request.getTemplateVariables())) {
            template.setTemplateVariables(request.getTemplateVariables());
        }
        templateDao.save(template);
        return new TemplateResponse(template);
    }

    @Override
    public void deleteTemplate(final String id) {
        getTemplateForCurrentTenant(id);
        templateDao.deleteTemplateById(id);
    }

    private Template getTemplateForCurrentTenant(final String id) {
       return templateDao.findByTenantIdAndId(getCurrentTenantId(), id)
                .orElseThrow(() -> new ValidationException(TEMPLATE_NOT_EXISTS_WITH_ID_ERROR,
                        HttpStatus.BAD_REQUEST.value()));
    }
}
