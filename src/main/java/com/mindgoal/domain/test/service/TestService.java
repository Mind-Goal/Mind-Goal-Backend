package com.mindgoal.domain.test.service;

import com.mindgoal.common.error.ErrorCode;
import com.mindgoal.domain.test.dto.TemplateResponse;
import com.mindgoal.domain.test.entity.TestTemplate;
import com.mindgoal.domain.test.repository.QuestionRepository;
import com.mindgoal.domain.test.repository.TestResultRepository;
import com.mindgoal.domain.test.repository.TestTemplateRepository;
import java.util.List;
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
        List<TestTemplate> testTemplates = testTemplateRepository.findAll();
        if (testTemplates.isEmpty()) {
            throw new IllegalArgumentException("no test templates found");
        }
        return new TemplateResponse(testTemplates);
    }
}
