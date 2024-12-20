package com.mindgoal.domain.expert.entity;

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
@Table(name = "EXPERTS")
@Entity
@Getter
public class Expert extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "CAREER_YEARS")
    private int careerYears;

    @Column(name = "MATCH_COUNT")
    private int matchCount;

    @Column(name = "PHYSICAL_SCORE")
    private int physicalScore;

    @Column(name = "PRICE_PER_HOUR")
    private int pricePerHour;

    @Column(name = "TECH_SCORE")
    private int techScore;

    @Column(name = "MENTAL_SCORE")
    private int mentalScore;

    @Column(name = "RATING")
    private double rating;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "CAREER_HISTORY")
    private String careerHistory;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "INSTAGRAM_URL")
    private String instagramUrl;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "SPECIALITY")
    private String speciality;

    @Column(name = "TEACHING_POSITION")
    private String teachingPosition;

    @Column(name = "YOUTUBE_URL")
    private String youtubeUrl;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Expert expert = (Expert) object;
        return Objects.equals(id, expert.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
