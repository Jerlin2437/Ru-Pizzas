package com.example.softmeth4.pizzas;

import com.example.softmeth4.enums.Sauce;
import com.example.softmeth4.enums.Size;
import com.example.softmeth4.enums.Topping;

import java.util.ArrayList;

/**
 * This abstract class represents the basic layout for the different types of pizzas
 * that a customer can order. This class contains methods to represent a pizza order and/or its toppings in a string
 * representation, and methods to get and set information about pizzas.
 *
 * @author Jerlin Yuen, Jason Lei
 */
public abstract class Pizza {

    private static final int FORMAT_LENGTH_SPLICE = 2;

    protected ArrayList<Topping> toppings;
    protected Size size;
    protected Sauce sauce;
    protected boolean extraSauce;
    protected boolean extraCheese;

    /**
     * abstract method of getting pizza price
     * @return pizza price
     */
    public abstract double price();

    /**
     * Parameterized constructor allows for the creation of a pizza given size,
     * choice of extra sauce and/or extra cheese, and the respective toppings.
     *
     * @param size        - size of pizza
     * @param extraSauce  - choice of extra sauce
     * @param extraCheese - choice of extra cheese
     */
    public Pizza(Size size, boolean extraSauce, boolean extraCheese) {
        this.size = size;
        this.extraSauce = extraSauce;
        this.extraCheese = extraCheese;
        toppings = new ArrayList<>();
    }

    /**
     * Returns a string representation of a pizza's type, size, whether it has
     * extra sauce/cheese, its toppings, and the price of the pizza.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        String pizzaString = "Type: " + getPizzaType() + "\n" +
                "Size: " + size + "\n" +
                "Extra Sauce: " + extraSauce + "\n" +
                "Extra Cheese: " + extraCheese + "\n" +
                "Toppings: " + toppingsToString() + "\n" +
                "Price: $" + String.format("%.2f", price()) + "\n";

        return pizzaString;
    }

    /**
     * Getter method (accessor)
     *
     * @return pizza type
     */
    protected abstract String getPizzaType();

    /**
     * Setter method (mutator)
     *
     * @param extraSauce - choice of extra sauce
     */
    public void setExtraSauce(boolean extraSauce) {
        this.extraSauce = extraSauce;
    }

    /**
     * Setter method (mutator)
     *
     * @param extraCheese - choice of extra cheese
     */
    public void setExtraCheese(boolean extraCheese) {
        this.extraCheese = extraCheese;
    }

    /**
     * Checks if customer has chosen extra sauce
     *@param extraSauce whether or not you want extra sauce
     * @return true if choice of extra sauce is selected, false otherwise
     */
    public boolean hasExtraSauce(boolean extraSauce) {
        return extraSauce;
    }

    /**
     * Checks if customer has chosen extra cheese
     * @param extraCheese whether or not you want extra cheese
     * @return true if choice of extra cheese is selected, false otherwise
     */
    public boolean hasExtraCheese(boolean extraCheese) {
        return extraCheese;
    }

    /**
     * Returns a string representation of a pizza's toppings
     *
     * @return string representation, "None" if no toppings in the array
     */
    private String toppingsToString() {
        if (toppings.isEmpty()) {
            return "None";
        } else {
            StringBuilder toppingsString = new StringBuilder();
            for (Topping topping : toppings) {
                toppingsString.append(topping).append(", ");
            }
            toppingsString.setLength(toppingsString.length() - FORMAT_LENGTH_SPLICE);
            return toppingsString.toString();
        }
    }

}
