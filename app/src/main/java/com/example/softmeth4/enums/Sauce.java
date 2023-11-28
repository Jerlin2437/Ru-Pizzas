package com.example.softmeth4.enums;

/**
 * This enum class provides a list of the two types of sauces that can go on the pizzas:
 * tomato and alfredo. This class also contains a method for taking in a string representation
 * of a sauce and returning its respective enum value.
 *
 * @author Jerlin Yuen
 */
public enum Sauce {
    TOMATO,
    ALFREDO;

    /**
     * The fromString method takes in a string representation of a sauce and returns
     * its respective enum value.
     *
     * @param sauceString - specific sauce
     * @return TOMATO if string "tomato," ALFREDO if string "alfredo," or throws an illegal argument with an error message if other input
     */
    public static Sauce fromString(String sauceString) {
        return switch (sauceString.toLowerCase()) {
            case "tomato" -> TOMATO;
            case "alfredo" -> ALFREDO;
            default -> throw new IllegalArgumentException("Invalid sauce: " + sauceString);
        };
    }
}