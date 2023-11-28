package com.example.softmeth4.enums;

import java.util.Arrays;

/**
 * This enum class provides a list of different possible toppings for a pizza.
 * This class includes a method to return a specific enum value for a topping given
 * a specific string representation.
 *
 * @author Jerlin Yuen
 */
public enum Topping {
    SAUSAGE,
    BEEF,
    PEPPERONI,
    HAM,
    ONION,
    GREEN_PEPPER,
    MUSHROOM,
    BLACK_OLIVE,
    SHRIMP,
    SQUID,
    CRAB_MEAT,
    SPAM,
    FISH;

    /**
     * The fromString method takes in a string representation of a topping and returns
     * its respective enum value.
     *
     * @param toppingString - specific topping
     * @return respective enum topping given a specific string representation of a topping,
     * or throws an illegal argument with an error message if other input (ex: returns MUSHROOM if given "mushroom")
     */
    public static Topping fromString(String toppingString) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(toppingString))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid topping: " + toppingString));
    }

}
