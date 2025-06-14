package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.AchievementStatus;

import java.time.LocalDateTime;

public class EarnedAchievement {
    private final Long id;
    private final Long achievementId;
    private final Long userId;
    private final LocalDateTime obtainedAt;
    private AchievementStatus status;
    private final int progress;

    public EarnedAchievement(Long id, Long achievementId, Long userId, LocalDateTime obtainedAt, AchievementStatus status, int progress) {
        this.id = id;
        this.achievementId = achievementId;
        this.userId = userId;
        this.obtainedAt = obtainedAt;
        this.status = status;
        this.progress = progress;
    }

    public EarnedAchievement(Long id, Long achievementId, Long userId, LocalDateTime obtainedAt, AchievementStatus status) {
        this.id = id;
        this.achievementId = achievementId;
        this.userId = userId;
        this.obtainedAt = obtainedAt;
        this.status = status;
        this.progress = 0;
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

    public void setStatus(AchievementStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getAchievementId() {
        return achievementId;
    }

    public int getProgress() {
        return progress;
    }
}
