package com.example.completableFuture;


import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class CompletableFutureExercise {
    private static final Random random = new Random();

    public static void main(String[] args) {
        long start = System.nanoTime();
        Future<Double> futurePrice = getPriceAsync2("my favorite produce");
        long invocationTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Invocation returned after " + invocationTime);

        doSomethingElse();

        try {
            double price = futurePrice.get();
            System.out.println("price = " + price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = (System.nanoTime() - start) / 1_000_000;
        System.out.println("retrievalTime = " + retrievalTime);
    }

    private static void doSomethingElse() {
        System.out.println("CompletableFutureExercise.doSomethingElse");
    }


    public static Future<Double> getPriceAsync1(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = caculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                futurePrice.completeExceptionally(e);
            }
        }).start();
        return futurePrice;
    }

    public static Future<Double> getPriceAsync2(String product) {
        return CompletableFuture.supplyAsync(() -> caculatePrice(product));
    }

    public static double caculatePrice(String product) {
        delay();
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }


    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
