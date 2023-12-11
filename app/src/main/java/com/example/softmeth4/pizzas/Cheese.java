package com.example.softmeth4.pizzas;

import com.example.softmeth4.enums.Sauce;
import com.example.softmeth4.enums.Size;
import com.example.softmeth4.enums.Topping;

public class Cheese extends Pizza{
    private static final double PRICE = 8.99;

    /**
     * Parameterized constructor allows for the creation of a pizza given size,
     * choice of extra sauce and/or extra cheese, and the respective toppings.
     *
     * @param size        - size of pizza
     * @param extraSauce  - choice of extra sauce
     * @param extraCheese - choice of extra cheese
     */
    public Cheese(Size size, boolean extraSauce, boolean extraCheese) {
        super(size, extraSauce, extraCheese);
        toppings.add(Topping.ONION);
        sauce = Sauce.TOMATO;
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
        return PRICE + size.getPrice() + extraCost;
    }

    /**
     * Getter method (accessor)
     *
     * @return "Cheese"
     */
    @Override
    protected String getPizzaType() {
        return "Cheese";
    }
}
