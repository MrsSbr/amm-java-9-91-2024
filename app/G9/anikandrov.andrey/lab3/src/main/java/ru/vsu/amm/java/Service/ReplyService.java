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

        return brands.stream()
                .filter(brand -> replyList.stream()
                .filter(reply -> reply.getCarBrand().equals(brand))
                        .count() == maxCount)
                        .toList();
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

            int mostPopularIndex = IntStream.range(0, brandCounts.length)
                    .boxed()
                    .max((i, j) -> Long.compare(brandCounts[i], brandCounts[j]))
                    .orElse(-1);

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
                .toList();

        Set<CarBrand> duplicates = allCars.stream()
                .filter(car -> Collections.frequency(allCars, car) > 1)
                .collect(Collectors.toSet());

        return allCars.stream()
                .filter(car -> !duplicates.contains(car))
                .distinct()
                .toList();
    }
}
