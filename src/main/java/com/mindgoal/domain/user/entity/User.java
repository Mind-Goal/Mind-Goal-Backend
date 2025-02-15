package com.mindgoal.domain.user.entity;

import com.mindgoal.common.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@Entity
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "PROFILE_IMAGE")
    private String profileImage;

    @Column(name = "IS_AGREE_POLICY")
    private Boolean isAgreePolicy;

    @Column(name = "IS_DELETED", nullable = false)
    private boolean isDeleted = false;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;

    @Builder
    public User(Long id, String email, String name, String phoneNumber,
                String profileImage, Boolean isAgreePolicy) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.isAgreePolicy = isAgreePolicy;
        this.isDeleted = false;
    }

    public User(String email, String name, String phoneNumber,
                String profileImage, Boolean isAgreePolicy) {
        this(null, email, name, phoneNumber, profileImage, isAgreePolicy);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        User user = (User) object;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void updateProfile(String name, String profileImage) {
        if (name != null) {
            this.name = name;
        }
        if (profileImage != null) {
            this.profileImage = profileImage;
        }
    }

    public void withdraw() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
