import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.main.FileReader.FileReader;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileReaderTest {
    private final FileReader fileReader = new FileReader();

    @Test
    public void testUnCorrectPath() {
        assertEquals(fileReader.read("abc"), new ArrayList<>());
    }

    @Test
    public void testUnCorrectPath2() {
        assertEquals(fileReader.read("C:/Users/777/IdeaProjects/amm-java-9-91-2024/app" +
                "/G9/gavrilov.mihail/lab4/src/main/java/ru/vsu/amm/java/main/test.txt"),
                new ArrayList<>());
    }
}
