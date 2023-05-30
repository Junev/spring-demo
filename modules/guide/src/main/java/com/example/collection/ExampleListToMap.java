package com.example.collection;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExampleListToMap {
    public static void listToMap() {
        List<Pair<String, Double>> pairList = new ArrayList<>(3);
        pairList.add(new Pair<>("version", 12.3));
        pairList.add(new Pair<>("version", 12.19));
        pairList.add(new Pair<>("version", 6.28));

        Map<String, Double> map = pairList.stream()
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v2));

        pairList.add(new Pair<>("version2", null));
        Map<String, Double> map1 = pairList.stream()
                .filter(c -> c.getValue() != null)
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue, (v1, v2) -> v2));
    }
}
