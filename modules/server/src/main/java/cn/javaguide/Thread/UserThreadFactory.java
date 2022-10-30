package cn.javaguide.Thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class UserThreadFactory implements ThreadFactory {
    private final String namePrefix;

    private final AtomicInteger nextId = new AtomicInteger(1);

    UserThreadFactory(String whatFeatureOfGroup) {
        namePrefix = "From UserThreadFactory's " + whatFeatureOfGroup + "-Worker-";
    }

    @Override
    public Thread newThread(Runnable task) {
        String name = namePrefix + nextId.getAndIncrement();
        Thread thread = new Thread(null, task, name, 0);
        System.out.println(name);
        return thread;
    }
}
