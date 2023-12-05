package com.example.softmeth4.businesslogic;

import com.example.softmeth4.enums.Sauce;
import com.example.softmeth4.enums.Size;
import com.example.softmeth4.enums.Topping;
import com.example.softmeth4.pizzas.*;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This class contains a method responsible for creating instances of various pizza types
 * given a string representation of a pizza.
 *
 * @author Jerlin Yuen
 */

public class PizzaMaker {
    private static final int EXTRASAUCE_PARSE = 2;
    private static final int EXTRACHEESE_PARSE = 3;
    private static final int SAUCE_PARSE = 4;
    private static final int TOPPING_PARSE = 5;

    //takes string of Pizzatype + more args depending on pizzatype.

    /**
     * Given a string representation of a pizza, it parses the string and creates a pizza of a respective pizza type,
     * of a specific size, considers whether it has extra sauce and/or cheese, and adds the toppings given a list of toppings.
     * @param pizzaType type of pizza to be parsed
     * @return a pizza of a specific pizza type, containing everything parsed from the string representation of the pizza
     */
    public static Pizza createPizza(String pizzaType) {
        String[] parsedPizza = pizzaType.split(" ");
        Size size = Size.fromString(parsedPizza[1]);
        boolean extraSauce = Boolean.parseBoolean(parsedPizza[EXTRASAUCE_PARSE]);
        boolean extraCheese = Boolean.parseBoolean(parsedPizza[EXTRACHEESE_PARSE]);
        if (Objects.equals(parsedPizza[0], "Supreme"))
            return new Supreme(size, extraSauce, extraCheese);
        else if (Objects.equals(parsedPizza[0], "Seafood"))
            return new Seafood(size, extraSauce, extraCheese);
        else if (Objects.equals(parsedPizza[0], "Chinese"))
            return new Chinese(size, extraSauce, extraCheese);
        else if (Objects.equals(parsedPizza[0], "Korean"))
            return new Korean(size, extraSauce, extraCheese);
        else if (Objects.equals(parsedPizza[0], "Cheese"))
            return new Cheese(size, extraSauce, extraCheese);
        else if (Objects.equals(parsedPizza[0], "Proteen"))
            return new Proteen(size, extraSauce, extraCheese);
        else if (Objects.equals(parsedPizza[0], "Spham"))
            return new Spham(size, extraSauce, extraCheese);
        else if (Objects.equals(parsedPizza[0], "Meatzza"))
            return new Meatzza(size, extraSauce, extraCheese);
        else if (Objects.equals(parsedPizza[0], "Deluxe"))
            return new Deluxe(size, extraSauce, extraCheese);
        else if (Objects.equals(parsedPizza[0], "Pepperoni"))
            return new Pepperoni(size, extraSauce, extraCheese);
        else if (Objects.equals(parsedPizza[0], "BYO")) {
            Sauce sauce = Sauce.fromString(parsedPizza[SAUCE_PARSE]);
            ArrayList<Topping> toppings = new ArrayList<>();
            for (int x = TOPPING_PARSE; x < parsedPizza.length; x++) {
                toppings.add(Topping.fromString(parsedPizza[x]));
            }
            return new BuildYourOwn(size, extraSauce, extraCheese, sauce, toppings);
        }
        return null;
    }
}
