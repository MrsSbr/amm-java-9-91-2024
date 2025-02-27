package test;

import org.junit.jupiter.api.Test;
import records.DrinkRecord;
import service.FileWorker;

import java.io.File;

import java.util.List
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static service.Logg.logger;

class FileWorkerTest {
    private static final String TEST_FILE = "test.txt";

    @Test
    void testSaveToFile() {
        FileWorker fileWorker = new FileWorker();
        try {
            fileWorker.saveToFile(TEST_FILE, 2);
            List<DrinkRecord> records = fileWorker.loadFromFile(TEST_FILE);

            assertEquals(2, records.size());

            logger.info("testing save file method end");
        } catch (Exception e) {
            logger.throwing(FileWorker.class.getName(), "testSaveToFile", e);

            fail("Ошибка при записи или чтении файла: " + e.getMessage());
        } finally {
            logger.fine("deleted file " + TEST_FILE);

            new File(TEST_FILE).delete();
        }
    }
}
