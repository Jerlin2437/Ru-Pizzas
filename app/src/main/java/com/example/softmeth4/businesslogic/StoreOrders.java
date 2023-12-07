package com.example.softmeth4.businesslogic;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manages all pizza orders in the store, containing methods to add and remove orders in the store,
 * and get information about various store orders.
 *
 * @author Jerlin Yuen, Jason Lei
 */
public final class StoreOrders {
    private static StoreOrders instance;
    private static int nextOrderNum;
    private final ArrayList<Order> orders;

    /**
     * Private constructor to prevent instantiation from outside the class.
     */
    private StoreOrders() {
        nextOrderNum = 1;
        orders = new ArrayList<>();
    }

    /**
     * Method to get the singleton instance of StoreOrders.
     *
     * @return The singleton instance.
     */
    public static synchronized StoreOrders getInstance() {
        if (instance == null) {
            instance = new StoreOrders();
        }
        return instance;
    }

    /**
     * Adds a new order to the collection of store orders
     * Sets the order number and increments the order number for the next order
     *
     * @param order - specific order
     */
    public void addOrder(Order order) {
        order.setOrderNumber(nextOrderNum);
        nextOrderNum++;
        orders.add(order);
    }

    /**
     * Returns a string representation of all store orders
     *
     * @return string representation of store orders
     */
    @Override
    public String toString() {
        StringBuilder storeOrdersString = new StringBuilder();
        for (Order order : orders) {
            storeOrdersString.append(order.toString());
            storeOrdersString.append("\n");
        }
        return storeOrdersString.toString();
    }

    /**
     * Getter method (accessor)
     *
     * @return nextOrderNum
     */
    public static int getNextOrderNum() {
        return nextOrderNum;
    }

    /**
     * Removes an order from the collection of store orders
     *
     * @return true, if order with a specific order number is found and removed, false otherwise
     */
    public boolean removeOrder(int orderNumber) {
        for (int x = 0; x < getTotalOrders(); x++) {
            if (orderNumber == orders.get(x).getOrderNumber()) {
                orders.remove(x);
                return true;
            }
        }
        return false;
    }

    /**
     * Getter method (accessor)
     *
     * @return size of list of orders
     */
    public int getTotalOrders() {
        return orders.size();
    }

    /**
     * Getter method (accessor)
     *
     * @return list of orders, all store orders
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * Looks through all orders and returns a specific order based on its order number,
     * if it's found.
     *
     * @return specific order
     */
    public Order getOrderByNumber(int orderNumber) {
        for (Order order : orders) {
            if (order.getOrderNumber() == orderNumber) {
                return order;
            }
        }
        //if order number not found
        return null;
    }
}
