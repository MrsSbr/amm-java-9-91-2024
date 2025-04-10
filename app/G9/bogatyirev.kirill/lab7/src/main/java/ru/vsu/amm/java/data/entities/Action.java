package ru.vsu.amm.java.data.entities;

public class Action {
    private Long id;
    private String name;
    private int points;


    public Action(Long id, String name, int points) {
        this.id = id;
        this.name = name;
        this.points = points;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

}
