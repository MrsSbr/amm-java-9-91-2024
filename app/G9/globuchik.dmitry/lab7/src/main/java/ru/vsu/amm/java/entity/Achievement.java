package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.AchievementType;

public class Achievement {
    private Long id;
    private String name;
    private String description;
    private AchievementType type;

    public Achievement(Long id, String name, String description, AchievementType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
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
