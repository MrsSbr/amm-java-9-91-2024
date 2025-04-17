package ru.vsu.amm.java.domain.entities;

public class WordToAction {
    private Long id;
    private String word;
    private Action action;

    public WordToAction(Long id, String word, Action action) {
        this.id = id;
        this.word = word;
        this.action = action;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public Action getAction() {
        return action;
    }

}
