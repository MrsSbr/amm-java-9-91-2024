package ru.vsu.amm.java.enums;

public enum PerfomanceName {
    NIGTH_BEFORE_CHRISTMAS("Ночь перед рождеством"),
    SHELCUNCHIC("Щелкунчик"),
    HAMLET("Гамлет"),
    ALICE_IN_WONDERLAND("Алиса в стране чудес"),
    ROMEO_AND_JULIET("Ромео и Джульетта"),
    MASTER_AND_MARGARET("Мастер и Маргарита"),
    SILVER_HOOF("Серебрянное копытце"),
    SECRET_ROOM("Тайная комната"),
    TWELFTH_CHEER("Двенадцать стульев"),
    DUKE_IGOR("Князь Игорь");

    private final String name;

    PerfomanceName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
