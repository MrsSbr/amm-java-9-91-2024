package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.classes.TestClassWithInterface;
import ru.vsu.amm.java.classes.TestClassWithoutAnnotation;
import ru.vsu.amm.java.classes.TestClassWithoutInterface;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InterfaceExtractorTest {
    private static final String PATH_TO_DIRECTORY = "ru.vsu.amm.java.classes";
    private static final String PATH_TO_NOT_EXIST_DIRECTORY = "ru.vsu.amm.nonexistent";

    @Test
    void testExtractInterfacesWithAnnotatedClass() {
        List<Class<?>> interfaces = InterfaceExtractor.extractInterfaces(TestClassWithInterface.class);
        assertEquals(1, interfaces.size());
        assertEquals(Runnable.class, interfaces.getFirst());
    }

    @Test
    void testExtractInterfacesWithAnnotatedClassNoInterface() {
        List<Class<?>> interfaces = InterfaceExtractor.extractInterfaces(TestClassWithoutInterface.class);
        assertTrue(interfaces.isEmpty());
    }

    @Test
    void testExtractInterfacesWithoutAnnotation() {
        List<Class<?>> interfaces = InterfaceExtractor.extractInterfaces(TestClassWithoutAnnotation.class);
        assertTrue(interfaces.isEmpty());
    }

    @Test
    void testGetInterfacesWithAnnotatedClasses(){
        Map<Class<?>, List<Class<?>>> result = InterfaceExtractor.getInterfaces(PATH_TO_DIRECTORY);
        assertTrue(result.containsKey(TestClassWithInterface.class));
        assertTrue(result.containsKey(TestClassWithoutInterface.class));
        assertFalse(result.containsKey(TestClassWithoutAnnotation.class));

        assertEquals(1, result.get(TestClassWithInterface.class).size());
        assertEquals(Runnable.class, result.get(TestClassWithInterface.class).getFirst());

        assertTrue(result.get(TestClassWithoutInterface.class).isEmpty());
    }

    @Test
    void testGetInterfacesWithoutAnnotatedClasses(){
        Map<Class<?>, List<Class<?>>> result = InterfaceExtractor.getInterfaces(PATH_TO_NOT_EXIST_DIRECTORY);
        assertTrue(result.isEmpty());
    }
}