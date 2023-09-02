package org.singleton;

// Initialization on Demand Holder
public class IoDHSingleton {
    private IoDHSingleton() {
    }

    private static class HolderClass {
        private final static IoDHSingleton instance = new IoDHSingleton();
    }

    public static IoDHSingleton getInstance() {
        return HolderClass.instance;
    }
}
