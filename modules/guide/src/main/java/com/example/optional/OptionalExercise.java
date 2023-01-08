package com.example.optional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OptionalExercise {
    public static void main(String[] args) {
        Insurance insurance = new Insurance();
        Optional<Insurance> optionalInsurance = Optional.ofNullable(insurance);
        Optional<String> optionalS = optionalInsurance.map(Insurance::getName);

        Person person = new Person();
        Optional<Person> optPerson = Optional.ofNullable(person);
        String optName = getCarInsuranceName(optPerson);
        System.out.println(optName);

        ifPresent(optPerson);
        orElseGet(optPerson);
        checkMultipleOptional(optionalInsurance, optPerson);
    }

    private static void checkMultipleOptional(Optional<Insurance> optionalInsurance,
                                              Optional<Person> optPerson) {
        List<Optional<?>> optionals = Arrays.asList(optionalInsurance, optPerson,
                optPerson.flatMap(Person::getCarAsOptional));
        boolean allMatch = optionals.stream().allMatch(Optional::isPresent);
        System.out.println("allMatch: " + allMatch);
    }

    private static void orElseGet(Optional<Person> optPerson) {
        Optional<Car> optionalCar = optPerson.flatMap(Person::getCarAsOptional);
        optionalCar.orElseGet(() -> new Car());
    }

    private static void ifPresent(Optional<Person> optPerson) {
        // NOTE: MUST flatMap
        optPerson.map(Person::getCar)
                .ifPresent(car -> System.out.println(car));

        optPerson.flatMap(Person::getCarAsOptional).ifPresent((c) -> {
            System.out.println(c);
        });
    }

    private static String getCarInsuranceName(Optional<Person> optPerson) {
        String optName =
                optPerson.flatMap(Person::getCarAsOptional)
                        .flatMap(Car::getInsuranceAsOptional)
                        .map(Insurance::getName)
                        .orElse("Unknown");
        return optName;

    }

}
