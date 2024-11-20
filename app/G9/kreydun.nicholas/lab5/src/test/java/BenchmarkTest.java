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
        // Создаем прокси для сервиса с отслеживанием времени
        Service trackedService = Benchmark.track(service);

        // Вызываем методы сервиса, чтобы проверить их выполнение
        trackedService.do1();
        trackedService.do2();
        trackedService.do3();

        // Получаем статистику времени выполнения
        Stat stat = Benchmark.getStat(trackedService);

        // Проверяем, что статистика не пустая (методы должны быть выполнены)
        assertNotNull(stat, "Stat should not be null");

        Logg.logger.fine("Testing method...");

        printResult(stat);
    }

    @Test
    public void testMethodExecutionTime() throws Exception {
        service = new ServiceImpl();
        // Создаем прокси для сервиса с отслеживанием времени
        Service trackedService = Benchmark.track(service);

        // Вызываем метод do1
        trackedService.do1();

        // Получаем статистику
        Stat stat = Benchmark.getStat(trackedService);

        // Проверяем, что время выполнения метода do1 зафиксировано
        assertNotNull(stat, "Stat should not be null");

        Logg.logger.fine("Testing method...");

        printResult(stat);
    }

    @Test
    public void testMultipleMethodExecution() throws Exception {
        service = new ServiceImpl();
        // Создаем прокси для сервиса
        Service trackedService = Benchmark.track(service);

        // Вызываем несколько методов
        trackedService.do1();
        trackedService.do2();

        // Получаем статистику по времени выполнения
        Stat stat = Benchmark.getStat(trackedService);

        // Проверяем, что время выполнения каждого метода зафиксировано
        assertNotNull(stat, "Stat should not be null");

        Logg.logger.fine("Testing method...");

        printResult(stat);
    }

    @Test
    public void testNoBenchmarkAnnotation() throws Exception {
        service = new ServiceImpl();
        // Создаем прокси для сервиса, который не использует аннотацию @Benchmarked
        Service trackedService = Benchmark.track(service);

        boolean hasAnnotatedMethod = Arrays.stream(ServiceImpl.class.getDeclaredMethods())
                .allMatch(method -> method.isAnnotationPresent(Benchmarked.class));

        trackedService.do3();

        // Получаем статистику
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
