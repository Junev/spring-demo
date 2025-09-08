package com.example.mytask.service;

import com.example.repository.model.PdsEquipproperty;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OpcValueSubject {
    Set<OpcValueListener> listeners;
    ExecutorService executorService;

    public OpcValueSubject() {
        listeners = new HashSet<>();
        // Create a thread pool for asynchronous notifications
        executorService = Executors.newCachedThreadPool();
    }

    void addListener(OpcValueListener listener) {
        listeners.add(listener);
    }

    void removteListener(OpcValueListener listener) {
        for (OpcValueListener l : listeners) {
            if (l.equals(listener)) {
                listeners.remove(listener);
            }
        }
    }

    void notify(Map<String, PdsEquipproperty> eps) {
        // Asynchronously notify each listener to prevent interference
        listeners.forEach(c -> 
            executorService.submit(() -> c.update(eps))
        );
    }
}