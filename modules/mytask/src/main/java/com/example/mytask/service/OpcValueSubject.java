package com.example.mytask.service;

import com.example.repository.model.PdsEquipproperty;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OpcValueSubject {
    Set<OpcValueListener> listeners;

    public OpcValueSubject() {
        listeners = new HashSet<>();
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

    void notify(Map<String, PdsEquipproperty> eps, String unitId) {
        listeners.forEach(c -> {
            c.update(eps, unitId);
        });
    }
}
