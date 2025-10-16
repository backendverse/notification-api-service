package com.notification.api.models.response;

import com.notification.api.models.entity.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateResponseDTO {

    private String id;
    private String name;
    private Map<String, String> templateVariables;

    public TemplateResponseDTO(final Template template) {
        BeanUtils.copyProperties(template, this);
        setId(template.getId().toString());
    }
}
