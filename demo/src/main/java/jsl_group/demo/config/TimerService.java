package jsl_group.demo.config;

import io.micrometer.core.annotation.Timed;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TimerService {

    @Timed(value = "do.timer.sleep.aop")
    public Long doSleep(Long milliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException(interruptedException);
        }
        return milliSeconds;
    }
}
