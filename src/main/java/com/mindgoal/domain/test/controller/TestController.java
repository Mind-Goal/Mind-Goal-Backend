package com.mindgoal.domain.test.controller;

import com.mindgoal.domain.test.dto.QuestionResponse;
import com.mindgoal.domain.test.dto.TemplateResponse;
import com.mindgoal.domain.test.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping
    public ResponseEntity<TemplateResponse> getAllTests() {
        TemplateResponse templateResponse = testService.findTestTemplates();
        return ResponseEntity.ok(templateResponse);
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<QuestionResponse> getTestDetail(@PathVariable long templateId) {
        QuestionResponse response = testService.findQuestion(templateId);
        return ResponseEntity.ok(response);
    }
}
