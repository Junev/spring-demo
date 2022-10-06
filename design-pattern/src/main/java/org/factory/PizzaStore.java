package org.factory;

import org.simplefactary.Pizza;

public abstract class PizzaStore {
    abstract Pizza createPizza(String type);

    public Pizza orderPizza(String type) {
        Pizza pizza = this.createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

}
