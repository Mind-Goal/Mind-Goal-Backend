package com.mindgoal.domain.expert.repository;

import com.mindgoal.domain.expert.dto.ExpertSearchCondition;
import com.mindgoal.domain.expert.entity.Expert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpertRepositoryCustom {
    Page<Expert> findAllByFilters(ExpertSearchCondition condition, Pageable pageable);
}