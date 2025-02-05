package com.mindgoal.domain.expert.repository;

import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.common.BaseResponseStatus;
import com.mindgoal.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>, ExpertRepositoryCustom {
    boolean existsByUserId(Long userId);

    default Expert findExpertById(Long id) {
        return findById(id)
                .orElseThrow(() -> new CustomException(BaseResponseStatus.EXPERT_NOT_FOUND));
    }
}