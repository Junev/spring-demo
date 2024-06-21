package com.example.mytask.service;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ScanOpcThreadFactory implements ThreadFactory {
    private final String namePrefix;

    private final AtomicInteger nextId = new AtomicInteger(1);

    ScanOpcThreadFactory(String whatFeatureOfGroup) {
        namePrefix = "From ScanOpcThreadFactory's " + whatFeatureOfGroup + "-Worker-";
    }

    @Override
    public Thread newThread(Runnable task) {
        String name = namePrefix + nextId.getAndIncrement();
        return new Thread(null, task, name, 0);
    }
}
