package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Enums.CarBrand;
import ru.vsu.amm.java.Constans.Constants;

import ru.vsu.amm.java.Entity.Reply;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
;

public class ReplyService {
    public static List<CarBrand> mostPopularBrand(List<Reply> replyList) {;
        if (replyList == null || replyList.isEmpty())
            return new ArrayList<>();

        Set<CarBrand> brands = replyList.stream()
                .map(Reply::getCarBrand)
                .collect(Collectors.toSet());

        Long maxCount = brands.stream()
                .map(carBrand -> replyList.stream()
                .filter(reply -> reply.getCarBrand().equals(carBrand))
                .count())
                .max(Comparator.comparingLong(Long::longValue))
                .orElse(0L);

        List<CarBrand> mostPopularBrands = brands.stream()
                .filter(brand -> replyList.stream()
                .filter(reply -> reply.getCarBrand().equals(brand))
                        .count() == maxCount)
                        .collect(Collectors.toList());

        return mostPopularBrands;
    }


    public static List<CarBrand> brandByAge(List<Reply> replyList) {
        if (replyList == null || replyList.isEmpty())
            return new ArrayList<>();

        List<CarBrand> ageBrandCount = new ArrayList<>(Constants.AGE_DIFF);

        IntStream.range(0, Constants.AGE_DIFF).forEach(i -> ageBrandCount.add(null));

        IntStream.range(Constants.MIN_AGE, Constants.MAX_AGE + 1).forEach(currentAge -> {
            long[] brandCounts = new long[CarBrand.values().length];

            replyList.stream()
                    .filter(reply -> reply.getAge() == currentAge)
                    .forEach(reply ->
                            brandCounts[reply.getCarBrand().ordinal()] ++);

            int mostPopularIndex = -1;
            long maxCount = 0;

            for (int i = 0; i < brandCounts.length; i++) {
                if (brandCounts[i] > maxCount) {
                    maxCount = brandCounts[i];
                    mostPopularIndex = i;
                }
            }

            if (mostPopularIndex != -1) {
                ageBrandCount.set(currentAge - Constants.MIN_AGE, CarBrand.values()[mostPopularIndex]);
            }
        });

        return ageBrandCount;
    }


    public static List<CarBrand> uniqueBrands(List<Reply> replyList) {
        if (replyList == null || replyList.isEmpty())
            return new ArrayList<>();

        List<CarBrand> allCars = replyList.stream()
                .map(Reply::getCarBrand)
                .collect(Collectors.toList());

        Set<CarBrand> duplicates = allCars.stream()
                .filter(car -> Collections.frequency(allCars, car) > 1)
                .collect(Collectors.toSet());


        List<CarBrand> uniqueBrands = allCars.stream()
                .filter(car -> !duplicates.contains(car))
                .distinct()
                .collect(Collectors.toList());

        return uniqueBrands;
    }
}
