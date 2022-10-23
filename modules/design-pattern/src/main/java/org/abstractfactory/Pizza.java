package org.abstractfactory;

import org.abstractfactory.Product.*;

public abstract class Pizza {
    String name;

    Dough dough;

    Sauce sauce;

    Veggies[] veggies;

    Cheese cheese;

    Pepperoni pepperoni;

    Clam clam;

    abstract void prepare();

    void bake() {
        System.out.println("Bake for 250 minutes at 350");

    }

    void cut() {
    }

    void box() {
    }


}
