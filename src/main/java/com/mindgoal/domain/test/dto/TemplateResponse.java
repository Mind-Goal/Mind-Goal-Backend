package com.mindgoal.domain.test.dto;

import com.mindgoal.domain.test.entity.TestTemplate;
import java.util.List;
import lombok.Getter;

@Getter
public record TemplateResponse(List<TestTemplate> templates) {
}
