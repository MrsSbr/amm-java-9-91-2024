package ru.vsu.amm.java;

import java.util.List;
import java.util.Map;

public class InterfaceAnnotationExtractor {

    public static void main(String[] args) {

        Map<Class<?>, List<Class<?>>> classInterfacesListMap = InterfaceExtractor.getInterfaces("ru.vsu.amm.java");
        for (Map.Entry<Class<?>, List<Class<?>>> entry : classInterfacesListMap.entrySet()) {
            Class<?> clazz = entry.getKey();
            System.out.println(clazz.getSimpleName());

            List<Class<?>> interfaces = entry.getValue();
            for (Class<?> anInterface : interfaces) {
                System.out.println("- " + anInterface.getSimpleName());
            }
        }
    }
}