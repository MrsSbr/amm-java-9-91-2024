package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.AchievementStatus;

import java.time.LocalDateTime;

public class EarnedAchievement {
    private Long id;
    private Long achievementId;
    private Long userId;
    private LocalDateTime obtainedAt;
    private AchievementStatus status;

    public EarnedAchievement(Long id, Long achievementId, Long userId, LocalDateTime obtainedAt, AchievementStatus status) {
        this.id = id;
        this.achievementId = achievementId;
        this.userId = userId;
        this.obtainedAt = obtainedAt;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getObtainedAt() {
        return obtainedAt;
    }

    public AchievementStatus getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAchievementId() {
        return achievementId;
    }

    public void setStatus(AchievementStatus status) {
        this.status = status;
    }
}
