package com.example.softmeth4;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.businesslogic.StoreOrders;
import com.example.softmeth4.pizzas.Pizza;

import java.util.List;

/**
 * This class is a fragment class that serves as a menu for displaying the current order in the pizza app.
 * The class provides ways to view, update, and remove the pizzas in a list, in the current order.
 *
 * @author Jason Lei, Jerlin Yuen
 */

public class fragment_current_order extends Fragment {
    private static final double TAX_RATE = 0.06625;
    private static final int ILLEGAL_INDEX = -1;
    private String currentOrderNumber;
    private Order order;
    private StoreOrders storeOrders;
    private double subtotalValue;
    private double salesTaxValue;
    private double orderTotalValue;
    private int selectedPosition;

    /**
     * Default empty constructor
     */
    public fragment_current_order() {
        // Required empty public constructor
    }

    /**
     * General required Android fragment onCreate method called upon creation
     * Initializes the 'order' variable by getting an instance of the 'Order' class
     * and 'storeOrders' by getting an instance of the 'StoreOrders' class
     *
     * @param savedInstanceState saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = Order.getInstance();
        storeOrders = StoreOrders.getInstance();
    }

    /**
     * Creates and returns the current order fragment's view.
     * Inflates the layout defined in 'fragment_current_order.xml'
     *
     * @param inflater           fragment xml class
     * @param container          container
     * @param savedInstanceState saved state
     * @return fragment_current_order layout view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_order, container, false);


        return view;
    }

    /**
     * This method is called after the view has been created, setting up UI components,
     * setting up event listeners, and initially updates the current order view
     *
     * @param view               display view layout
     * @param savedInstanceState saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = getView().findViewById(R.id.currentOrderView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener((parent, currentView, position, id) -> {
            selectedPosition = position;
        });
        updateCurrentOrderView();
        view.findViewById(R.id.placeOrder).setOnClickListener(v -> placeOrder());
//        view.findViewById(R.id.refreshOrder).setOnClickListener(v -> updateCurrentOrderView());
        view.findViewById(R.id.removePizza).setOnClickListener(v -> removePizza());
    }

    /**
     * This method removes a selected pizza from the current order and updates the order view
     */
    private void removePizza() {
        if (Order.getInstance().getPizzas().isEmpty()) {
            showRemoveButEmptyPopup();
        } else {
            ListView listOfPizzas = getView().findViewById(R.id.currentOrderView);
            if (selectedPosition != ILLEGAL_INDEX) {
                Pizza selectedPizza = Order.getInstance().getPizzas().get(selectedPosition);
                Order.getInstance().removePizza(selectedPizza);
                updateCurrentOrderView();
                showRemovedPopup();
            }
        }
    }

    /**
     * This method places an order, creates a new order, and adds it to store orders.
     * This method also resets/clears the current order, and updates the order view.
     */
    private void placeOrder() {
        ListView listOfPizzas = getView().findViewById(R.id.currentOrderView);
        if (!order.getPizzas().isEmpty() && listOfPizzas.getCount() == order.getPizzas().size()) {
            showAddedPopup();
            updateCurrentOrderView();
            Order currentOrder = Order.createNewOrder();
            StoreOrders.getInstance().addOrder(currentOrder);
            Order.getInstance().resetOrder();
            updateCurrentOrderView();
        } else {
            showEmptyPopup();
        }
    }

    /**
     * This method updates the UI to display the current list of pizzas in the order, including order number,
     * subtotal, sales tax, and total.
     */
    private void updateCurrentOrderView() {
        List<Pizza> pizzas = order.getPizzas();
        //  ObservableList<String> observableList = FXCollections.observableArrayList(pizzaSummaries);
        ListView listOfPizzas = getView().findViewById(R.id.currentOrderView);

        ArrayAdapter<Pizza> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, pizzas);
        listOfPizzas.setAdapter(adapter);


        currentOrderNumber = String.valueOf(StoreOrders.getNextOrderNum());
        TextView orderNumber = getView().findViewById(R.id.setOrderNumber);
        orderNumber.setText(currentOrderNumber);

        calculateSubtotal();
        calculateSalesTax();
        calculateOrderTotal();

    }

    /**
     * This method calculates and updates the displayed value for subtotal
     */
    private void calculateSubtotal() {
        subtotalValue = 0.0;
        for (Pizza pizza : order.getPizzas()) {
            subtotalValue += pizza.price();
        }
        TextView subtotal = getView().findViewById(R.id.subtotal);
        subtotal.setText(String.format("%.2f", subtotalValue));
    }

    /**
     * Calculates the sales tax of the pizza order.
     * The sales tax in NJ is 6.625%.
     */
    private void calculateSalesTax() {
        //6.625% sales tax in NJ
        double taxRate = TAX_RATE;
        salesTaxValue = subtotalValue * taxRate;
        TextView salesTax = getView().findViewById(R.id.salesTax);
        salesTax.setText(String.format("%.2f", salesTaxValue));
    }

    /**
     * Calculates the total amount of the pizza order.
     */
    private void calculateOrderTotal() {
        orderTotalValue = subtotalValue + salesTaxValue;
        TextView orderTotal = getView().findViewById(R.id.orderTotal);
        orderTotal.setText(String.format("%.2f", orderTotalValue));
    }

    /**
     * This method shows an alert dialog for a pizza that has been successfully removed
     */
    private void showRemovedPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Pizza Removed Successfully")
                .setMessage("Pizza Removed!")
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * This method shows an alert dialog for a pizza that has been successfully added
     */
    private void showAddedPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Pizza Order Added Successfully")
                .setMessage("Order placed!")
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * This method shows a toast message when no pizzas have been added to the order yet
     */
    private void showEmptyPopup() {
        showToast("Not all pizzas have been added to the order yet! Please consider refreshing.");
    }

    /**
     * This method shows a toast message when there are no pizzas in the order to be removed (empty order)
     */
    private void showRemoveButEmptyPopup() {
        showToast("There are no pizzas in the order to remove.");
    }

    /**
     * A general toast message formatted method to display a specific message
     */
    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}