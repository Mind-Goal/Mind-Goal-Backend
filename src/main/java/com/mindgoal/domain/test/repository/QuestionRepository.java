package com.mindgoal.domain.test.repository;

import com.mindgoal.domain.test.entity.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<TestQuestion, Long> {
}
