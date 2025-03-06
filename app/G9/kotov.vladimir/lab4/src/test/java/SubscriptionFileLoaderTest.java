import ru.vsu.amm.java.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SubscriptionFileLoaderTest {

    private final SubscriptionFileLoader loader = new SubscriptionFileLoader();

    @Test
    public void testLoadNonexistentFile() {
        List<SubscriberRecord> result = loader.loadFile("testLoadNonexistentFile.txt");
        assertTrue(result.isEmpty());
    }
}
