package com.mindgoal.backend.support.fixture.test;

import com.mindgoal.domain.test.entity.TestTemplate;
import com.mindgoal.domain.test.entity.TestType;

public class TestTemplateFixture {
    public static TestTemplate 경기력_템플릿() {
        return new TestTemplate(TestType.GAME_PERFORMANCE, "제목1", "설명1");
    }

    public static TestTemplate 불안감_템플릿() {
        return new TestTemplate(TestType.ANXIETY, "제목1", "설명1");
    }

    public static TestTemplate 훈련_템플릿() {
        return new TestTemplate(TestType.TRAINING, "제목1", "설명1");
    }

    public static TestTemplate 기능_자신감_템플릿() {
        return new TestTemplate(TestType.SKILL_CONFIDENCE, "제목1", "설명1");
    }

    public static TestTemplate 부상_템플릿() {
        return new TestTemplate(TestType.INJURY, "제목1", "설명1");
    }

    public static TestTemplate 관계_문제_템플릿() {
        return new TestTemplate(TestType.RELATIONSHIP, "제목1", "설명1");
    }

    public static TestTemplate 진로_문제_템플릿() {
        return new TestTemplate(TestType.CAREER, "제목1", "설명1");
    }

    public static TestTemplate 학업_스트레스_템플릿() {
        return new TestTemplate(TestType.STUDY, "제목1", "설명1");
    }

    public static TestTemplate 생활_문제_템플릿() {
        return new TestTemplate(TestType.LIFESTYLE, "제목1", "설명1");
    }
}
