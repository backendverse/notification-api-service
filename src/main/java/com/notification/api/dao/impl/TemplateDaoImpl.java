package com.notification.api.dao.impl;

import com.notification.api.dao.interfaces.CacheService;
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
import java.util.function.Supplier;

import static com.notification.api.utils.CommonUtils.getCurrentTenantId;
import static com.notification.api.utils.CommonUtils.isNotEmpty;

@Service
@Slf4j
@RequiredArgsConstructor
class TemplateDaoImpl implements TemplateDao {

    private final TemplateRepository templateRepository;
    private CacheService cacheService;

    @Override
    public Optional<Template> findByTenantIdAndName(final String tenantId, final String templateName) {
        return cacheService.getByName(tenantId, templateName, Template.class)
                .or(() -> templateRepository.findByNameIgnoreCaseAndTenantId(templateName, tenantId)
                        .map(template -> {
                            cacheService.putByName(tenantId, templateName, template);
                            return template;
                        }));
    }

    @Override
    public Optional<Template> findByTenantIdAndId(final String tenantId, final String id) {
        return cacheService.getById(tenantId, id, Template.class).or(() -> {
            return templateRepository.findByIdAndTenantId(id, tenantId).map(template -> {
                cacheService.putById(tenantId, id, template);
                return template;
            });
        });
    }

    @Override
    public Template save(final Template template) {
        cacheService.putById(template.getTenantId(), template.getId(), template);
        cacheService.putByName(template.getTenantId(), template.getName(), template);
        return templateRepository.save(template);
    }

    @Override
    public Page<Template> filterTemplate(final Example<Template> example,
                                         final PageRequest pageRequest) {
        return templateRepository.findAll(example, pageRequest);
    }

    @Override
    public void deleteTemplateById(final String id, final Supplier<? extends Throwable> exceptionHandler) {
        findByTenantIdAndId(getCurrentTenantId(), id).ifPresentOrElse(template -> {
            cacheService.deleteById(template.getTenantId(), template.getId());
            cacheService.deleteByName(template.getTenantId(), template.getName());
            templateRepository.deleteById(id);
        }, () -> {
            if (isNotEmpty(exceptionHandler)) {
                exceptionHandler.get();
            }
        });
    }
}
