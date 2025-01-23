package com.mindgoal.backend.support.fixture;

import com.mindgoal.domain.test.entity.TestQuestion;

public class TestQuestionFixture {
    public static TestQuestion 경기력_질문1() {
        return new TestQuestion(1L, "질문내용1");
    }

    public static TestQuestion 경기력_질문2() {
        return new TestQuestion(1L, "질문내용2");
    }

    public static TestQuestion 경기력_질문3() {
        return new TestQuestion(1L, "질문내용3");
    }

    public static TestQuestion 불안감_질문1() {
        return new TestQuestion(2L, "질문내용4");
    }
}
