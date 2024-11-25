import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Annotation.Benchmarked;

import ru.vsu.amm.java.impl.ServiceImpl;
import ru.vsu.amm.java.service.Service;
import ru.vsu.amm.java.service.Logg;
import ru.vsu.amm.java.banchmarks.Benchmark;
import ru.vsu.amm.java.banchmarks.Stat;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BenchmarkTest {

    private Service service;

    @Test
    public void testTrackedService() throws Exception {
        service = new ServiceImpl();

        Service trackedService = Benchmark.track(service);

        trackedService.do1();
        trackedService.do2();
        trackedService.do3();

        Stat stat = Benchmark.getStat(trackedService);

        assertNotNull(stat, "Stat should not be null");

        Logg.logger.fine("Testing method...");

        printResult(stat);
    }

    @Test
    public void testMethodExecutionTime() throws Exception {
        service = new ServiceImpl();

        Service trackedService = Benchmark.track(service);

        trackedService.do1();

        Stat stat = Benchmark.getStat(trackedService);

        assertNotNull(stat, "Stat should not be null");

        Logg.logger.fine("Testing method...");

        printResult(stat);
    }

    @Test
    public void testMultipleMethodExecution() throws Exception {
        service = new ServiceImpl();

        Service trackedService = Benchmark.track(service);

        trackedService.do1();
        trackedService.do2();

        Stat stat = Benchmark.getStat(trackedService);

        assertNotNull(stat, "Stat should not be null");

        Logg.logger.fine("Testing method...");

        printResult(stat);
    }

    @Test
    public void testNoBenchmarkAnnotation() throws Exception {
        service = new ServiceImpl();

        Service trackedService = Benchmark.track(service);

        boolean hasAnnotatedMethod = Arrays.stream(ServiceImpl.class.getDeclaredMethods())
                .allMatch(method -> method.isAnnotationPresent(Benchmarked.class));

        trackedService.do3();

        Stat stat = Benchmark.getStat(trackedService);

        assertFalse(hasAnnotatedMethod, "It has not annotated method");

        Logg.logger.fine("Testing method...");

        printResult(stat);
    }

    void printResult(Stat st) {
        System.out.println("Execution Times:");
        st.print(System.out);
    }
}
