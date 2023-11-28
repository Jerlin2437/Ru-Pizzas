package com.example.softmeth4.enums;

/**
 * This enum class provides a list of different sizes for a pizza.
 * This class also contains methods to return the price given a size and return
 * a specific enum value given a specific string representation.
 *
 * @author Jerlin Yuen
 */
public enum Size {
    SMALL(0),
    MEDIUM(2.00),
    LARGE(4.00);


    private final double price;

    /**
     * Parameterized constructor
     *
     * @param price - price given a specific size
     */
    Size(double price) {
        this.price = price;
    }

    /**
     * Getter method (accessor)
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * The fromString method takes in a string representation of a size and returns
     * its respective enum value.
     *
     * @param sizeString - specific size
     * @return SMALL if string "small," MEDIUM if string "medium," LARGE if string "large,"
     * or throws an illegal argument with an error message if other input
     */
    public static Size fromString(String sizeString) {
        return switch (sizeString.toLowerCase()) {
            case "small" -> SMALL;
            case "medium" -> MEDIUM;
            case "large" -> LARGE;
            default -> throw new IllegalArgumentException("Invalid size: " + sizeString);
        };
    }
}
