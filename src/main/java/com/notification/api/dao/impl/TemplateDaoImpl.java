package com.notification.api.dao.impl;

import com.notification.api.dao.interfaces.TemplateDao;
import com.notification.api.dao.repostiories.TemplateRepository;
import com.notification.api.models.entity.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateDaoImpl implements TemplateDao {

    private final TemplateRepository templateRepository;

    @Override
    public Optional<Template> findByTenantIdAndName(final String tenantId, final String templateName) {
        return templateRepository.findByNameIgnoreCaseAndTenantId(templateName, tenantId);
    }

    @Override
    public Optional<Template> findByTenantIdAndId(final String tenantId, final String id) {
        return templateRepository.findByIdAndTenantId(id, tenantId);
    }

    @Override
    public Template save(final Template template) {
        return templateRepository.save(template);
    }

    @Override
    public Page<Template> filterTemplate(final Example<Template> example,
                                         final PageRequest pageRequest) {
        return templateRepository.findAll(example, pageRequest);
    }

    @Override
    public void deleteTemplateById(final String id) {
        templateRepository.deleteById(id);
    }
}
