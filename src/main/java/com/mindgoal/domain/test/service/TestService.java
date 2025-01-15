package com.mindgoal.domain.test.service;

import com.mindgoal.domain.test.dto.TemplateResponse;
import com.mindgoal.domain.test.repository.QuestionRepository;
import com.mindgoal.domain.test.repository.TestResultRepository;
import com.mindgoal.domain.test.repository.TestTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TestService {
    private final QuestionRepository questionRepository;
    private final TestResultRepository testResultRepository;
    private final TestTemplateRepository testTemplateRepository;

    @Transactional(readOnly = true)
    public TemplateResponse findTestTemplates() {
        return new TemplateResponse(testTemplateRepository.findAll());
    }
}
