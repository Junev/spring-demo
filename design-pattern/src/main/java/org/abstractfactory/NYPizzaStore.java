package org.abstractfactory;

import org.abstractfactory.factory.PizzaIngredientFactory;
import org.abstractfactory.factory.impl.NYPizzaIngradientPizzaFactory;

public class NYPizzaStore extends PizzaStore {
    Pizza createPizza(String type) {
        Pizza pizza = null;
        PizzaIngredientFactory factory = new NYPizzaIngradientPizzaFactory();

        if (type.equals("cheese")) {
            pizza = new CheesePizza(factory);
        }

        return pizza;

    }
}
