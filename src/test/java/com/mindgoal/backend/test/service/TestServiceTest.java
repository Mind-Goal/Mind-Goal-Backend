package com.mindgoal.backend.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.mindgoal.backend.support.annotation.ServiceTest;
import com.mindgoal.backend.support.config.TestConfig;
import com.mindgoal.backend.support.fixture.test.TestQuestionFixture;
import com.mindgoal.backend.support.fixture.test.TestTemplateFixture;
import com.mindgoal.domain.test.entity.TestTemplate;
import com.mindgoal.domain.test.repository.QuestionRepository;
import com.mindgoal.domain.test.repository.TestTemplateRepository;
import com.mindgoal.domain.test.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@ServiceTest
public class TestServiceTest {
    @Autowired
    private TestTemplateRepository testTemplateRepository;

    @Autowired
    private QuestionRepository questionRepository;

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
    void 테스트_템플릿이_존재하지_않을경우_예외_발생() {
        assertThatThrownBy(() -> testService.findTestTemplates())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 템플릿별_테스트_조회() {
        TestTemplate performanceTemplate = testTemplateRepository.save(TestTemplateFixture.경기력_템플릿());
        TestTemplate anxietyTemplate = testTemplateRepository.save(TestTemplateFixture.불안감_템플릿());

        questionRepository.save(TestQuestionFixture.경기력_질문1());
        questionRepository.save(TestQuestionFixture.경기력_질문2());
        questionRepository.save(TestQuestionFixture.경기력_질문3());
        questionRepository.save(TestQuestionFixture.불안감_질문1());

        assertThat(testService.findQuestion(performanceTemplate.getId()).getQuestions().size()).isEqualTo(3);
        assertThat(testService.findQuestion(anxietyTemplate.getId()).getQuestions().size()).isEqualTo(1);
    }

    @Test
    void 템플릿에_테스트가_등록이_안되있을_경우_예외발생() {
        TestTemplate performanceTemplate = testTemplateRepository.save(TestTemplateFixture.경기력_템플릿());
        assertThatThrownBy(() -> testService.findQuestion(performanceTemplate.getId())).isInstanceOf(
                IllegalArgumentException.class);
    }
}
