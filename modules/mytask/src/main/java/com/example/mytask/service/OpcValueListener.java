package com.example.mytask.service;

import com.example.repository.model.PdsEquipproperty;

import java.util.Map;

public interface OpcValueListener {
    void init();
    void update(Map<String, PdsEquipproperty> eps);
}
