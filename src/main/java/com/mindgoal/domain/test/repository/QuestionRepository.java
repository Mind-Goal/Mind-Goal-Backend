package com.mindgoal.domain.test.repository;

import com.mindgoal.domain.test.entity.TestQuestion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<TestQuestion, Long> {
    List<TestQuestion> findAllByTemplateId(Long templateId);
}
