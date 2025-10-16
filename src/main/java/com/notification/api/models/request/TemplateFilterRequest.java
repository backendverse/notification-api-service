package com.notification.api.models.request;

import com.notification.api.models.entity.Template;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TemplateFilterRequest extends BaseSearchDTO<Template> {

    private String name;

    @Override
    public Class<Template> getEntity() {
        return Template.class;
    }
}
