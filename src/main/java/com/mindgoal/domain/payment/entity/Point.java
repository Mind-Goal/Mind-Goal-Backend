package com.mindgoal.domain.payment.entity;

import com.mindgoal.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POINTS")
@Entity
@Getter
public class Point extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "AMOUNT", nullable = false)
    private int amount;

    @Column(name = "POINT_TYPE")
    private String pointType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "EXPIRED_AT")
    private LocalDate expiredAt;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Point point = (Point) object;
        return Objects.equals(id, point.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
