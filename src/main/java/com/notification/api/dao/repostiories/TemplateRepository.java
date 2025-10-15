package com.notification.api.dao.repostiories;

import com.notification.api.models.entity.Template;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface TemplateRepository extends MongoRepository<Template, UUID> {

    Optional<Template> findByNameIgnoreCaseAndTenantId(String name, UUID tenantId);

}
