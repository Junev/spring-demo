package cn.javaguide.Thread;

import java.time.Instant;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TheadPractice {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                4,
                6,
                1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),
                new UserThreadFactory("u1"),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 20; i++) {
            threadPoolExecutor.execute(() -> {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Current thread name: "+ Thread.currentThread().getName() +" " +
                        "date: " + Instant.now());
            });
        }

        threadPoolExecutor.shutdown();

        try {
            threadPoolExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Finished all threads");
    }
}
