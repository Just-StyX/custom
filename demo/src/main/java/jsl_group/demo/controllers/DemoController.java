package jsl_group.demo.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import jsl_group.demo.config.TimerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

@Controller
public class DemoController {
    private final Timer timerSleep;
    private final TimerService timerService;

    public DemoController(MeterRegistry meterRegistry, TimerService timerService) {
        this.timerSleep = meterRegistry.timer("do.timer.sleep", "timer", "sleep");
        this.timerService = timerService;
    }

    @GetMapping("/home")
    public String index(Model model, @RequestParam(defaultValue = "500") Long milliseconds) {
        Long result = timerSleep.record(() -> doSleep(milliseconds));
        Long resultAop = timerService.doSleep(milliseconds);
        model.addAttribute("milliseconds", resultAop);
        return "index";
    }

    private Long doSleep(Long milliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
        return milliSeconds;
    }
}
