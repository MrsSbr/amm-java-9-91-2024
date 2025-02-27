package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    private Long id;

    private String code;

    private String name;

    private String sign;
}
