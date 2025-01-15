package com.mindgoal.backend.test.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.mindgoal.backend.support.annotation.ServiceTest;
import com.mindgoal.backend.support.fixture.TestTemplateFixture;
import com.mindgoal.domain.test.repository.TestTemplateRepository;
import com.mindgoal.domain.test.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceTest
public class TestServiceTest {
    @Autowired
    private TestTemplateRepository testTemplateRepository;
    @Autowired
    private TestService testService;

    @Test
    void 테스트_템플릿_전체_조회() {
        testTemplateRepository.save(TestTemplateFixture.경기력_템플릿());
        testTemplateRepository.save(TestTemplateFixture.관계_문제_템플릿());
        testTemplateRepository.save(TestTemplateFixture.부상_템플릿());
        testTemplateRepository.save(TestTemplateFixture.불안감_템플릿());

        assertThat(testService.findTestTemplates().getTemplates().size()).isEqualTo(4);
    }

    @Test
}
