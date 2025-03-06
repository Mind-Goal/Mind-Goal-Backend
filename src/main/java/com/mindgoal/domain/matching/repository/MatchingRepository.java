package com.mindgoal.domain.matching.repository;

import com.mindgoal.domain.matching.entity.Matching;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 매칭 정보를 데이터베이스에서 관리하는 레포지토리 인터페이스
 */
@Repository
public interface MatchingRepository extends JpaRepository<Matching, Long> {

    /**
     * 사용자 ID로 매칭 목록 조회
     *
     * @param userId 사용자 ID
     * @return 매칭 목록
     */
    List<Matching> findAllByUserId(Long userId);
}