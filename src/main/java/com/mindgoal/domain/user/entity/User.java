package com.mindgoal.domain.user.entity;

import com.mindgoal.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "MEMBER")
@Entity
@Getter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "LOGIN_ID", nullable = false)
    private String loginId;

    @Column(name = "LOGIN_PW", nullable = false)
    private String loginPassword;

    @Column(name = "USER_NAME")
    private String username;

    @Column(name = "BIRTH_DATE")
    private String birthDate;

    @Column(name = "PROFILE_IMAGE")
    private String profileImage;

    @Column(name = "IS_AGREE_POLICY")
    private Boolean isAgreePolicy;



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
}
