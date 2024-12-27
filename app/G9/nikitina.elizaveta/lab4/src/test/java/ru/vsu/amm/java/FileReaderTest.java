package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.FileService.FileReader;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileReaderTest {

    private final FileReader fileReader = new FileReader();

    @Test
    public void testUnCorrectPath() {
        assertEquals(fileReader.read("abc"), new ArrayList<>());
    }
}