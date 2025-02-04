package com.mindgoal.domain.expert.repository;

import com.mindgoal.domain.expert.entity.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>, ExpertRepositoryCustom {
    boolean existsByUserId(Long userId);

    default Expert findExpertByUserId(Long userId) {
        return findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 전문가입니다."));
    }
}
