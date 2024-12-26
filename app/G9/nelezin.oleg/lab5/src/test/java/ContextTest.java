import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.Context;
import ru.vsu.amm.java.service.impl.FirstServiceImpl;
import ru.vsu.amm.java.service.impl.SecondServiceImpl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ContextTest {

    private Context context;

    @BeforeEach
    public void setup() {
        context = new Context();
    }

    @Test
    public void testContextInitialization() {
        FirstServiceImpl firstService = context.getBean(FirstServiceImpl.class);
        SecondServiceImpl secondService = context.getBean(SecondServiceImpl.class);

        assertNotNull(firstService, "FirstService must be initialized");
        assertNotNull(secondService, "SecondService must be initialized");
    }

    @Test
    public void testInjection() {
        FirstServiceImpl firstService = context.getBean(FirstServiceImpl.class);

        assertNotNull(firstService, "FirstService must be initialized");
        assertNotNull(firstService.getSecondService(), "SecondService must be injected");
    }

    @Test
    public void testNonExistentBeans() {
        Object notExistentBean = context.getBean(Integer.class);

        assertNull(notExistentBean, "Non existent bean must return null");
    }
}
