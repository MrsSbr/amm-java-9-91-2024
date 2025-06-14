package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.AchievementType;

public class Achievement {
    private final Long id;
    private final String name;
    private final String description;
    private final AchievementType type;
    private final int requiredProgress;

    public Achievement(Long id, String name, String description, AchievementType type, int requiredProgress) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.requiredProgress = requiredProgress;
    }

    public int getRequiredProgress() {
        return requiredProgress;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public AchievementType getType() {
        return type;
    }

}
