package at.fhj.monitoringdemo.controller;

import at.fhj.monitoringdemo.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/api/hello")
    public String hello() {
        return demoService.getHelloMessage();
    }

    @GetMapping("/api/slow")
    public String slow() throws InterruptedException {
        return demoService.getSlowResponse();
    }

    @GetMapping("/api/error")
    public String error() {
        return demoService.throwDemoError();
    }

    @GetMapping("/api/vote")
    public String vote() {
        return demoService.vote();
    }

    @GetMapping("/api/status")
    public String status() {
        return demoService.status();
    }
}