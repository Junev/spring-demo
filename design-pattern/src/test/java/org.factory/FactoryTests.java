package org.factory;

import org.junit.jupiter.api.Test;
import org.simplefactary.Pizza;

class FactoryTests {
    @Test
    public void createPizza() {
        PizzaStore pizzaStore = new NYStylePizzaStore();
        Pizza pizza = pizzaStore.orderPizza("cheese");
    }
}