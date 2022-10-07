package org.abstractfactory;

import org.abstractfactory.factory.PizzaIngredientFactory;

public class CheesePizza extends Pizza {
    PizzaIngredientFactory factory;

    public CheesePizza(PizzaIngredientFactory factory) {
        this.factory = factory;
    }

    void prepare() {
        this.dough = factory.createDough();
        this.sauce = factory.createSauce();
        this.cheese = factory.createCheese();
    }
}
