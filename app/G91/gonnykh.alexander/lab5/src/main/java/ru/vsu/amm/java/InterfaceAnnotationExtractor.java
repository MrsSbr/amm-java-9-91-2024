package ru.vsu.amm.java;

import ru.vsu.amm.java.logger.LoggerConfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class InterfaceAnnotationExtractor {
    private static final Logger logger = Logger.getLogger(InterfaceAnnotationExtractor.class.getName());

    public static void main(String[] args) {
        try {
            LoggerConfig.configure();
        Map<Class<?>, List<Class<?>>> classInterfacesListMap = InterfaceExtractor.getInterfaces("ru.vsu.amm.java");
        for (Map.Entry<Class<?>, List<Class<?>>> entry : classInterfacesListMap.entrySet()) {
            Class<?> clazz = entry.getKey();
            System.out.println(clazz.getSimpleName());

            List<Class<?>> interfaces = entry.getValue();
            for (Class<?> anInterface : interfaces) {
                System.out.println("- " + anInterface.getSimpleName());
            }
        }
    } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
}