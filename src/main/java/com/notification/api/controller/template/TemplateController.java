package com.notification.api.controller.template;

import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.UpdateTemplateRequest;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.services.interfaces.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<FilterTemplateResponse> filterTemplate(TemplateFilterRequest request) {
        return ResponseEntity.ok(templateService.filterTemplate(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TemplateResponse> updateTemplate(@PathVariable String id,@RequestBody UpdateTemplateRequest request) {
        return ResponseEntity.ok(templateService.updateTemplate(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTemplate(@PathVariable String id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("Template Deleted Successfully");
    }

}
