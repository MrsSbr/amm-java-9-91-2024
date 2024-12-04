package ru.vsu.amm.java.context;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.services.Service1;
import ru.vsu.amm.java.services.Service2;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {

    public static final String packagePath = "ru.vsu.amm.java";
    private final Context context = new Context(packagePath);

    @Test
    void initializeContextTest() {
        assertNotNull(context.get(Service2.class));
        assertNotNull(context.get(Service1.class));
    }

    @Test
    void injectDependenciesTest() {
        Service2 service2 = context.get(Service2.class);
        assertNotNull(service2.getService1());
    }

    @Test
    void notExistTest() {
        assertNull(context.get(Void.class));
    }
}