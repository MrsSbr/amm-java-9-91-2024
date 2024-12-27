package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DetailsService {
    public static void generateList(int partsCount, List<Part> parts) {
        for (int i = 0; i < partsCount; i++) {
            parts.add(new Part(DetailTypes.getRandomType()));
        }
    }

    public static void print(List<Part> parts) {
        for (Part part : parts) {
            System.out.println(part.getType());
        }
    }

    public static Set<DetailTypes> getUniqueDetailTypes(List<Part> parts) {
        return parts.stream()
                .map(Part::getType)
                .collect(Collectors.toSet());
    }

    public static List<String> countDetails(List<Part> parts) {
        Set<DetailTypes> uniqueDetailTypes = getUniqueDetailTypes(parts);
        List<String> result = new ArrayList<>(uniqueDetailTypes.size());
        for (DetailTypes detailType : uniqueDetailTypes) {
            int count = (int) parts.stream()
                    .filter(part -> part.getType() == detailType)
                    .count();
            result.add(detailType + ": " + count);
        }
        return result;
    }

    public static void addPart(DetailTypes detailType, List<Part> parts) {
        parts.add(new Part(detailType));
    }
}
