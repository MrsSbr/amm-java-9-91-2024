package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.entity.Achievement;
import ru.vsu.amm.java.entity.EarnedAchievement;

public class EarnedAchievementView {
    private final EarnedAchievement earned;
    private final Achievement achievement;

    public EarnedAchievementView(EarnedAchievement earned, Achievement achievement) {
        this.earned = earned;
        this.achievement = achievement;
    }

    public EarnedAchievement getEarned() {
        return earned;
    }

    public Achievement getAchievement() {
        return achievement;
    }

}
