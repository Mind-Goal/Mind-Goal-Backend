package com.mindgoal.domain.test.repository;

import com.mindgoal.domain.test.entity.TestTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestTemplateRepository extends JpaRepository<TestTemplate, Long> {
}
