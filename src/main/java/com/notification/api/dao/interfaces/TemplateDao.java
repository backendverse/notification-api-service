package com.notification.api.dao.interfaces;

import com.notification.api.models.entity.Template;

import java.util.Optional;

public interface TemplateDao {

    Optional<Template> findByTenantIdAndName(String tenantId, String templateName);

    Template save(Template template);

}
