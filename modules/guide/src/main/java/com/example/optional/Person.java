package com.example.optional;

import java.util.Optional;

public class Person {
    private Car car;

    public Optional<Car> getCarAsOptional() {
        return Optional.ofNullable(car);
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
