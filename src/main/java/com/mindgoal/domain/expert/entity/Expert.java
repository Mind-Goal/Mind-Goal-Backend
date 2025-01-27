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
import lombok.Builder;
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

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

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

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean isActive = false;

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

    @Column(name = "REGION", nullable = false)
    private String region;

    @Column(name = "SPECIALITY")
    private String speciality;

    @Column(name = "TEACHING_METHOD")
    private String teachingMethod;

    @Column(name = "YOUTUBE_URL")
    private String youtubeUrl;

    @Builder
    public Expert(Long userId, String category, String speciality, String position,
                  String region, int careerYears, String description, String careerHistory,
                  String teachingMethod, int pricePerHour, String youtubeUrl,
                  String instagramUrl, Boolean isActive, double rating, int matchCount,
                  int physicalScore, int techScore, int mentalScore) {
        this.userId = userId;
        this.category = category;
        this.speciality = speciality;
        this.position = position;
        this.region = region;
        this.careerYears = careerYears;
        this.description = description;
        this.careerHistory = careerHistory;
        this.teachingMethod = teachingMethod;
        this.pricePerHour = pricePerHour;
        this.youtubeUrl = youtubeUrl;
        this.instagramUrl = instagramUrl;
        this.isActive = (isActive != null) ? isActive : false;
        this.rating = rating;
        this.matchCount = matchCount;
        this.physicalScore = physicalScore;
        this.techScore = techScore;
        this.mentalScore = mentalScore;
    }

    public void update(
            String specialty,
            String position,
            String description,
            String careerHistory,
            String teachingMethod,
            Integer pricePerHour,
            String youtubeUrl,
            String instagramUrl
    ) {
        if (specialty != null) this.speciality = specialty;
        if (position != null) this.position = position;
        if (description != null) this.description = description;
        if (careerHistory != null) this.careerHistory = careerHistory;
        if (teachingMethod != null) this.teachingMethod = teachingMethod;
        if (pricePerHour != null) this.pricePerHour = pricePerHour;
        if (youtubeUrl != null) this.youtubeUrl = youtubeUrl;
        if (instagramUrl != null) this.instagramUrl = instagramUrl;
    }

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