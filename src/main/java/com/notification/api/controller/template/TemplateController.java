package com.notification.api.controller.template;

import com.common.sdk.models.interfaces.GenericAPIResponse;
import com.common.sdk.services.ResponseHandler;
import com.notification.api.models.request.CreateTemplateRequest;
import com.notification.api.models.request.TemplateFilterRequest;
import com.notification.api.models.request.UpdateTemplateRequest;
import com.notification.api.models.response.FilterTemplateResponse;
import com.notification.api.models.response.TemplateResponse;
import com.notification.api.services.interfaces.TemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/template")
public class TemplateController {

    private final TemplateService templateService;
    private final ResponseHandler responseHandler;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenericAPIResponse<TemplateResponse> createTemplate(@Valid @RequestBody CreateTemplateRequest request) {
        return responseHandler.ok(templateService.createTemplate(request));
//        return responseHandler.ok(List.of("dummy hello data"));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GenericAPIResponse<FilterTemplateResponse> filterTemplate(TemplateFilterRequest request) {
        return responseHandler.ok(templateService.filterTemplate(request));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GenericAPIResponse<TemplateResponse> updateTemplate(@PathVariable String id, @RequestBody UpdateTemplateRequest request) {
        return responseHandler.ok(templateService.updateTemplate(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public GenericAPIResponse<String> deleteTemplate(@PathVariable String id) {
        templateService.deleteTemplate(id);
        return responseHandler.ok("Template Deleted Successfully");
    }

}
