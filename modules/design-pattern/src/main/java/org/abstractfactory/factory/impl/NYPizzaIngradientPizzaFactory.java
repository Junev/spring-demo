package org.abstractfactory.factory.impl;

import org.abstractfactory.Product.*;
import org.abstractfactory.Product.impl.*;
import org.abstractfactory.factory.PizzaIngredientFactory;

public class NYPizzaIngradientPizzaFactory implements PizzaIngredientFactory {
    public Dough createDough() {
        return new ThinCrustDough();
    }

    public Sauce createSauce() {
        return new MarinaraSauce();
    }

    public Cheese createCheese() {
        return new ReggianoCheese();
    }

    public Veggies[] createVeggies() {
        return new Veggies[]{new Garlic()};
    }

    public Pepperoni createPepperoni() {
        return new SlicedPepperoni();
    }

    public Clam createClams() {
        return new FreshClams();
    }
}
