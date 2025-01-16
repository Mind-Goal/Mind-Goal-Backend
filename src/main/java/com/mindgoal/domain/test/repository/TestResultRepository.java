package com.mindgoal.domain.test.repository;

import com.mindgoal.domain.test.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {
}
