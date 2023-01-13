package com.example.completableFuture;

import java.util.concurrent.*;

public class FutureExercise {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Double> future = executorService.submit(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return doSomeLongComputation();
            }
        });

        doSomethingElse();

        try {
            Double result = future.get(1, TimeUnit.SECONDS);
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }

    private static void doSomethingElse() {
    }

    private static Double doSomeLongComputation() {
        return 2 * Math.asin(1);
    }
}
