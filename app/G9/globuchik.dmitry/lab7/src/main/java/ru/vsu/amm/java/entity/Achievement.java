package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.AchevementType;

public class Achievement {
    private Long id;
    private String name;
    private String description;
    private AchevementType type;

    public Achievement(Long id, String name, String description, AchevementType type) {
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

    public AchevementType getType() {
        return type;
    }
}
