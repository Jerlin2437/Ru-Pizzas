package com.example.softmeth4.pizzas;

import com.example.softmeth4.enums.Sauce;
import com.example.softmeth4.enums.Size;
import com.example.softmeth4.enums.Topping;

public class Proteen extends Pizza{
    private static final double PRICE = 15.99;
    /**
     * Parameterized constructor allows for the creation of a pizza given size,
     * choice of extra sauce and/or extra cheese, and the respective toppings.
     *
     * @param size        - size of pizza
     * @param extraSauce  - choice of extra sauce
     * @param extraCheese - choice of extra cheese
     */
    public Proteen(Size size, boolean extraSauce, boolean extraCheese) {
        super(size, extraSauce, extraCheese);
        toppings.add(Topping.BEEF);
        toppings.add(Topping.FISH);
        toppings.add(Topping.CRAB_MEAT);
        sauce = Sauce.ALFREDO;
    }

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

    @Override
    protected String getPizzaType() {
        return "Proteen";
    }
}
