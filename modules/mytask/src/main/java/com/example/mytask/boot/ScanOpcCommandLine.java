package com.example.mytask.boot;

import com.example.mytask.service.ScanOpcService;
import com.example.repository.model.PdsEquipproperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ScanOpcCommandLine implements CommandLineRunner {
    @Autowired
    private List<PdsEquipproperty> timeEps;

    @Autowired
    private ScanOpcService scanOpcService;

    @Override
    public void run(String... args) throws Exception {
        scanOpcService.run();

        /*
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(
                1);
        Runnable task = () -> {
            for (PdsEquipproperty p : timeEps) {
                System.out.println(p.getPropertyid() + " ," + p.getPropertyname() + " ," + p
                .getValue());
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 2, TimeUnit.MINUTES);
        */
    }
}
