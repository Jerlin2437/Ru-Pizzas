package com.example.softmeth4.pizzas;

import com.example.softmeth4.enums.Sauce;
import com.example.softmeth4.enums.Size;
import com.example.softmeth4.enums.Topping;

/**
 * This class represents a pizza of type "Deluxe" that a customer can order,
 * containing methods to return information about a pizza and its price after adding
 * toppings, its size, and extra sauce/cheese.
 *
 * @author Jerlin Yuen, Jason Lei
 */

public class Deluxe extends Pizza {
    private static final double DELUXE_PRICE = 14.99;

    /**
     * Parameterized constructor allows for the creation of a Deluxe pizza,
     * contains list of preset toppings and sauce.
     *
     * @param size        - size of pizza
     * @param extraSauce  - choice of extra sauce
     * @param extraCheese - choice of extra cheese
     */
    public Deluxe(Size size, boolean extraSauce, boolean extraCheese) {
        super(size, extraSauce, extraCheese);
        sauce = Sauce.TOMATO;
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.ONION);
        toppings.add(Topping.MUSHROOM);
    }

    /**
     * Getter method (accessor)
     *
     * @return "Deluxe"
     */
    @Override
    protected String getPizzaType() {
        return "Deluxe";
    }

    /**
     * Calculates and returns the price of the pizza based on the base cost,
     * the size of the pizza, whether there is extra sauce and/or extra cheese.
     *
     * @return price of the pizza
     */
    @Override
    public double price() {
        double extraCost = 0.0;
        if (hasExtraSauce(extraSauce)) {
            extraCost += 1.0;
        }
        if (hasExtraCheese(extraCheese)) {
            extraCost += 1.0;
        }
        return DELUXE_PRICE + size.getPrice() + extraCost;
    }
}
