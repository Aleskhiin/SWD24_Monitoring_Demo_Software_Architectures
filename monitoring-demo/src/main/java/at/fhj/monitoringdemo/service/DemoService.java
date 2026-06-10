package at.fhj.monitoringdemo.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final Counter helloCounter;
    private final Counter slowCounter;
    private final Counter errorCounter;
    private final Counter voteCounter;

    public DemoService(MeterRegistry meterRegistry) {
        this.helloCounter = Counter.builder("demo_hello_requests_total")
                .description("Counts calls to /api/hello")
                .register(meterRegistry);

        this.slowCounter = Counter.builder("demo_slow_requests_total")
                .description("Counts calls to /api/slow")
                .register(meterRegistry);

        this.errorCounter = Counter.builder("demo_error_requests_total")
                .description("Counts calls to /api/error")
                .register(meterRegistry);

        this.voteCounter = Counter.builder("demo_votes_total")
                .description("Counts calls to /api/vote")
                .register(meterRegistry);
    }

    public String getHelloMessage() {
        helloCounter.increment();
        return "Hello Monitoring!";
    }

    public String getSlowResponse() throws InterruptedException {
        slowCounter.increment();
        Thread.sleep(1500);
        return "This was a slow response.";
    }

    public String throwDemoError() {
        errorCounter.increment();
        throw new RuntimeException("Simulated error for monitoring demo.");
    }

    public String vote() {
        voteCounter.increment();
        return "Vote registered!";
    }

    public String status() {
        return "Application is running.";
    }
}