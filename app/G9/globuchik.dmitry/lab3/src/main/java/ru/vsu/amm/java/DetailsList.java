package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DetailsList {

    private List<Part> parts; //с помощью листа можно удобно хранить все детали с дублями

    public DetailsList(int PARTS_COUNT) {
        this.parts = new ArrayList<Part>(PARTS_COUNT);
        Random rand = new Random();
        for (int i = 0; i < PARTS_COUNT; i++) {
            parts.add(new Part(DetailTypes.getRandomType()));
        }
    }

    public void print() {
        for (Part part : parts) {
            System.out.println(part.getType());
        }
    }

    public List<DetailTypes> getUniqueDetailTypes() {
        return parts.stream()
                .map(Part::getType)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> countDetails() {
        List<DetailTypes> uniqueDetailTypes = getUniqueDetailTypes();
        List<String> result = new ArrayList<>();
        for (DetailTypes detailType : uniqueDetailTypes) {
            int count = (int)parts.stream()
                    .filter(part -> part.getType() == detailType)
                    .count();
            result.add(detailType + ": " + count);
        }
        return result;
    }
}
