package com.mindgoal.domain.test.dto;

import com.mindgoal.domain.test.entity.TestQuestion;
import java.util.List;
import lombok.Getter;

@Getter
public class QuestionResponse {
    private final List<TestQuestion> questions;

    public QuestionResponse(List<TestQuestion> questions) {
        this.questions = questions;
    }
}
