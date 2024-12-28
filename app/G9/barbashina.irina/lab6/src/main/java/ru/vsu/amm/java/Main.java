package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Address;
import ru.vsu.amm.java.entity.User;
import ru.vsu.amm.java.service.SqlGenerator;

public class Main {
    public static void main(String[] args) {
        SqlGenerator generator = new SqlGenerator();
        String sql1 = generator.generateSelectQuery(User.class);
        System.out.println(sql1);
        String sql2 = generator.generateSelectQuery(User.class, Address.class, "address");
        System.out.println(sql2);
    }
}