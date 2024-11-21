package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.FileService.FileReader;
import ru.vsu.amm.java.Model.TortureInstrument;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileReaderTest {

    private final FileReader fileReader = new FileReader();

    @Test
    public void testUnCorrectPath() {
       assertEquals(fileReader.read("abc"),new ArrayList<>());
    }
}
