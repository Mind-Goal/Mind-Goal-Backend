package com.mindgoal.domain.test.dto;

import com.mindgoal.domain.test.entity.TestTemplate;
import java.util.List;
import lombok.Getter;

@Getter
public class TemplateResponse {
    private final List<TestTemplate> templates;

    public TemplateResponse(List<TestTemplate> templates) {
        this.templates = templates;
    }
}
