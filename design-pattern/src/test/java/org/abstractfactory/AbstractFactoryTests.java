package org.abstractfactory;

import org.junit.jupiter.api.Test;

public class AbstractFactoryTests {
    @Test
    public void createPizza() {
        PizzaStore pizzaStore = new NYPizzaStore();
        Pizza pizza = pizzaStore.orderPizza("cheese");
    }
}
