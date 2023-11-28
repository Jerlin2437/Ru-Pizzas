package com.example.softmeth4.pizzas;

import com.example.softmeth4.enums.Sauce;
import com.example.softmeth4.enums.Size;
import com.example.softmeth4.enums.Topping;

import java.util.ArrayList;

/**
 * This class represents a customizable pizza (build your own!) a customer can order,
 * containing methods to return information about a pizza and its price after adding
 * toppings, its size, and extra sauce/cheese.
 *
 * @author Jerlin Yuen, Jason Lei
 */
public class BuildYourOwn extends Pizza {
    private static final double BYO_PRICE = 8.99;
    private static final int MIN_TOPPING = 3;
    private static final double ADDITIONAL_TOPPINGS = 1.49;

    /**
     * Parameterized constructor allows for the creation of a BYO pizza
     *
     * @param size        - size of pizza
     * @param extraSauce  - choice of extra sauce
     * @param extraCheese - choice of extra cheese
     * @param sauce       - type of sauce
     * @param toppings    - list of all toppings selected
     */
    public BuildYourOwn(Size size, boolean extraSauce, boolean extraCheese, Sauce sauce, ArrayList<Topping> toppings) {
        super(size, extraSauce, extraCheese);
        this.sauce = sauce;
        this.toppings = toppings;
    }

    /**
     * Getter method (accessor)
     *
     * @return "BYO"
     */
    @Override
    protected String getPizzaType() {
        return "BYO";
    }

    /**
     * Calculates and returns the price of the pizza based on the base cost,
     * the size of the pizza, whether there is extra sauce and/or extra cheese, and
     * the number of toppings (if there are more than 3 toppings).
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
        if (toppings.size() <= MIN_TOPPING) {
            return BYO_PRICE + size.getPrice() + extraCost;
        } else
            return (BYO_PRICE + size.getPrice() + (toppings.size() - MIN_TOPPING) * ADDITIONAL_TOPPINGS) + extraCost;
    }
}
