package com.example.thread;

import com.example.repository.model.PdsEquipproperty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TwoThread {
    private static volatile List<PdsEquipproperty> timeEps;

    public static void main(String[] args) {
        timeEps = new ArrayList<>(1);
        PdsEquipproperty ep = new PdsEquipproperty();
        ep.setValue(0L);
        timeEps.add(ep);

        ScheduledExecutorService scheduler1 = Executors.newScheduledThreadPool(1);

        Runnable task1 = () -> {
            timeEps.forEach(c -> {
                Long pre = (Long) c.getValue();
                Long cur = pre + 2;
                c.setValue(cur);
            });
            timeEps.forEach(c -> {
                System.out.println(c.getValue());
            });
        };
        scheduler1.scheduleAtFixedRate(task1, 0, 2, TimeUnit.SECONDS);


        ScheduledExecutorService scheduler2 = Executors.newScheduledThreadPool(1);

        Runnable task2 = () -> {
            timeEps.forEach(c -> {
                c.setValue(0L);
            });
            timeEps.forEach(c -> {
                System.out.println(c.getValue());
            });
        };
        scheduler2.scheduleAtFixedRate(task2, 0, 60, TimeUnit.SECONDS);
    }


}
