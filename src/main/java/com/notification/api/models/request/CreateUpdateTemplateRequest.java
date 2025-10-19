package com.notification.api.models.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
public class CreateUpdateTemplateRequest {

    @NotBlank(message = "Name Field Is Required")
    private String name;

    private Map<String, String> templateVariables;

    @NotBlank(message = "Message Template Field Is Required")
    private String messageTemplate;


}
