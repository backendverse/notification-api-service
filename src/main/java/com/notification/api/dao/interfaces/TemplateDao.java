package com.notification.api.dao.interfaces;

import com.notification.api.models.entity.Template;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface TemplateDao {

    Optional<Template> findByTenantIdAndName(String tenantId, String templateName);

    Template save(Template template);

    Page<Template> filterTemplate(Example<Template> templateExample, PageRequest pageRequest);
}
