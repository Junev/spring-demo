package org.simplefactary;

public abstract class Pizza {
    public void prepare() {
        System.out.println("Pizza prepare");
    }

    public void bake() {
        System.out.println("Pizza bake");
    }


    public void cut() {
        System.out.println("Pizza cut");
    }

    public void box() {
        System.out.println("Pizza box");
    }
}
