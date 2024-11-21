package ru.vsu.amm.java;

import ru.vsu.amm.java.Entity.Reply;
import ru.vsu.amm.java.Service.ReplyService;

import java.util.ArrayList;

public class PopularCarTask {

    public static void main(String[] args) {

        int replyCount = 1500;
        ArrayList<Reply> replyList = new ArrayList<>(replyCount);

        for (int i = 0; i < replyCount; ++i) {
            replyList.add(ReplyGenerator.Generator());
        }

        System.out.println("1. Most popular brand: ");
        ReplyService.mostPopularBrand(replyList).forEach(System.out::println);

        System.out.println("2. Popular brands by age: ");
        ReplyService.brandByAge(replyList).forEach(System.out::println);

        System.out.println("3. Unique brands: ");
        ReplyService.uniqueBrands(replyList).forEach(System.out::println);
    }

}