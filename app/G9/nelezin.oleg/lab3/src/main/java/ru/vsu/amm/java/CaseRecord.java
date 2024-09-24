package ru.vsu.amm.java;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class CaseRecord {

    private String respondent;

    private String plaintiff;

    private LocalDate date;

    private Article article;

    private boolean isConvicted;

    public CaseRecord() {}
}
