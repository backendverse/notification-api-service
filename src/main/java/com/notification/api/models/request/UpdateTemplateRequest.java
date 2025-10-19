package com.notification.api.models.request;

import lombok.Data;

import java.util.Map;

@Data
public class UpdateTemplateRequest {

    private String name;

    private Map<String, String> templateVariables;

    private String messageTemplate;


}
