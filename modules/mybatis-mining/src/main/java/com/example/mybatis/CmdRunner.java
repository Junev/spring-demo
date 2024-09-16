package com.example.mybatis;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class CmdRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
                10);
        Runnable task = () -> {
            System.out.println(LocalDateTime.now());
        };
        scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }
}
