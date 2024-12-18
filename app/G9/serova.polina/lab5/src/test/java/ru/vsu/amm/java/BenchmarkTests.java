package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.service.Service;
import ru.vsu.amm.java.service.ServiceMock;
import ru.vsu.amm.java.util.Benchmark;
import java.lang.reflect.Proxy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class BenchmarkTests {

    private final ServiceMock service;

    public BenchmarkTests() {
        service = new ServiceMock();
    }

    @BeforeEach
    public void reset() {
        service.reset();
        Benchmark.resetStats();
    }

    @Test
    public void testTrack() {
        // given & when
        Service trackedService = Benchmark.track(service);

        // then
        assertTrue(Proxy.isProxyClass(trackedService.getClass()));
    }

    @Test
    public void testResetStats() throws InterruptedException {
        // given
        Service trackedService = Benchmark.track(service);
        trackedService.do1();

        // when
        Benchmark.resetStats();

        // then
        assertNull(Benchmark.getStat(trackedService));
    }

    @Test
    public void testAnnotatedMethodInvocation() throws InterruptedException {
        // given
        Service trackedService = Benchmark.track(service);

        // when
        trackedService.do1();

        // then
        assertNotNull(Benchmark.getStat(trackedService));
    }

    @Test
    public void testAnnotatedMethodInvocationWhenMethodThrows() throws InterruptedException {
        // given
        service.shouldThrowException = true;
        Service trackedService = Benchmark.track(service);

        // when
        trackedService.do1();

        // then
        assertNull(Benchmark.getStat(trackedService));
    }

    @Test
    public void testNotAnnotatedMethodInvocation() throws InterruptedException {
        // given
        Service trackedService = Benchmark.track(service);

        // when
        trackedService.do2();

        // then
        assertNull(Benchmark.getStat(trackedService));
    }

    @Test
    public void testNotAnnotatedMethodInvocationWhenMethodThrows() throws InterruptedException {
        // given
        service.shouldThrowException = true;
        Service trackedService = Benchmark.track(service);

        // when
        trackedService.do2();

        // then
        assertNull(Benchmark.getStat(trackedService));
    }
}
