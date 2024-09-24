package ru.vsu.amm.java;

import java.time.LocalDate;

public class CaseRecord {

    private String respondent;

    private String plaintiff;

    private LocalDate date;

    private Article article;

    private boolean isConvicted;

    public CaseRecord(String respondent,
                      String plaintiff,
                      LocalDate date,
                      Article article,
                      boolean isConvicted) {
        this.respondent = respondent;
        this.plaintiff = plaintiff;
        this.date = date;
        this.article = article;
        this.isConvicted = isConvicted;
    }

    public CaseRecord() {}

    public String getPlaintiff() {
        return plaintiff;
    }

    public void setPlaintiff(String plaintiff) {
        this.plaintiff = plaintiff;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public boolean isConvicted() {
        return isConvicted;
    }

    public void setConvicted(boolean convicted) {
        isConvicted = convicted;
    }

    public String getRespondent() {
        return respondent;
    }

    public void setRespondent(String respondent) {
        this.respondent = respondent;
    }
}
