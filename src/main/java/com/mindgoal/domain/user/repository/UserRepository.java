package com.mindgoal.domain.user.repository;

import com.mindgoal.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndIsDeletedFalse(String email);

    boolean existsByEmailAndIsDeletedFalse(String email);

    default User findUserById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException(""));
    }

    default User findUserByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException(""));
    }
}
