package com.notification.api.controller;

import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.services.interfaces.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/template")
public class TemplateController {

    private final TemplateService templateService;

    @PostMapping
    public ResponseEntity<TemplateResponse> createTemplate(@Valid @RequestBody CreateTemplateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(templateService.createTemplate(request));
    }

}
