package com.example.softmeth4.businesslogic;

import com.example.softmeth4.pizzas.Pizza;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a customer's pizza order and contains methods to
 * get and set information about an order, calculate total costs for the order,
 * and methods to return information about an order in a string representation.
 *
 * @author Jerlin Yuen, Jason Lei
 */
public class Order {
    private static final double TAX_RATE = 0.06625;
    private int orderNumber;
    private final ArrayList<Pizza> pizzas;
    private double subTotalValue;
    private double salesTaxValue;
    private double orderTotalValue;
    private String finalSubTotal;
    private String finalSalesTax;
    private String finalOrderTotal;

    /**
     * Default constructor creates a new ArrayList of pizzas in an order
     */
    public Order() {
        pizzas = new ArrayList<>();
    }

    /**
     * Parameterized constructor allows for the creation of an order with specified list of pizzas
     *
     * @param pizzas - order with pizzas
     */
    public Order(ArrayList<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    /**
     * Adds a pizza to the order.
     *
     * @param pizza - specific pizza
     */
    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    /**
     * Removes a pizza from the order.
     *
     * @param pizza - pizza from the order
     */
    public void removePizza(Pizza pizza) {
        pizzas.remove(pizza);
    }

    /**
     * Getter method (accessor)
     *
     * @return pizzas
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Getter method (accessor)
     *
     * @return orderTotalValue
     */
    public double getOrderTotalValue() {
        return orderTotalValue;
    }

    /**
     * Getter method (accessor)
     *
     * @return orderNumber
     */
    public int getOrderNumber() {
        return orderNumber;
    }

    /**
     * Setter method (mutator)
     *
     * @param orderNumber - order number for a specific order
     */
    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * Returns a list of pizzas in the order with detailed information for each pizza.
     *
     * @return list of pizzas and detailed information for each, such as toppings, size, and price.
     */
    public List<String> getPizzaDetails() {
        List<String> pizzaDetails = new ArrayList<>();
        for (int i = 0; i < pizzas.size(); i++) {
            Pizza pizza = pizzas.get(i);
            pizzaDetails.add("Pizza " + (i + 1) + ": \n" + pizza.toString() + ("\n"));
        }
        return pizzaDetails;
    }

    /**
     * Returns a textual representation of a customer's order with each pizza and their respective details.
     *
     * @return - string textual representation
     */
    public String toString() {
        StringBuilder orderString = new StringBuilder();
        orderString.append("Order Number: ").append(orderNumber).append("\n");

        for (int i = 0; i < pizzas.size(); i++) {
            Pizza pizza = pizzas.get(i);
            orderString.append("Pizza ").append(i + 1).append(": ").append(pizza.toString()).append("\n");
        }

        return orderString.toString();
    }

    //instead of just subtotal, displays subtotal, sales tax, and order total

    /**
     * Returns a textual representation of a customer's order with each pizza and their respective details,
     * also contains values for subtotal, sales tax, and order total amount.
     *
     * @return - string textual representation
     */
    public String toFinalOrderDetailsString() {
        StringBuilder orderString = new StringBuilder();
        orderString.append("Order Number: ").append(orderNumber).append("\n");

        for (int i = 0; i < pizzas.size(); i++) {
            Pizza pizza = pizzas.get(i);
            orderString.append("Pizza ").append(i + 1).append(": ").append(pizza.toString()).append("\n");
        }
        calculateFinalOrderValues();
        orderString.append("Subtotal: $").append(finalSubTotal).append("\n");
        orderString.append("Sales Tax: $").append(finalSalesTax).append("\n");
        orderString.append("Order Total: $").append(finalOrderTotal).append("\n");

        return orderString.toString();
    }

    /**
     * Calculates values for subtotal, sales tax, and total amount for a pizza order.
     */
    private void calculateFinalOrderValues() {
        subTotalValue = 0.0;
        for (Pizza pizza : pizzas) {
            subTotalValue += pizza.price();
        }
        double taxRate = TAX_RATE;
        salesTaxValue = subTotalValue * taxRate;
        orderTotalValue = subTotalValue + salesTaxValue;

        finalSubTotal = String.format("%.2f", subTotalValue);
        finalSalesTax = String.format("%.2f", salesTaxValue);
        finalOrderTotal = String.format("%.2f", orderTotalValue);
    }

}
