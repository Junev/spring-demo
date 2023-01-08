package com.example.optional;

import java.util.Optional;

public class Car {
    private Insurance insurance;

    public Optional<Insurance> getInsuranceAsOptional() {
        return Optional.ofNullable(insurance);
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
}
