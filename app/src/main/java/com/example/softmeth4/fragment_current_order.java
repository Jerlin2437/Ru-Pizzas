package com.example.softmeth4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableList;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.businesslogic.StoreOrders;
import com.example.softmeth4.pizzas.Pizza;

import org.w3c.dom.Text;

import java.util.List;


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

    public fragment_current_order() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = Order.getInstance();
        storeOrders = StoreOrders.getInstance();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_current_order, container, false);


        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = getView().findViewById(R.id.currentOrderView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener((parent, currentView, position, id) -> {
            selectedPosition = position;
        });
        updateCurrentOrderView();
        view.findViewById(R.id.placeOrder).setOnClickListener(v-> placeOrder());
        view.findViewById(R.id.refreshOrder).setOnClickListener(v -> updateCurrentOrderView());
        view.findViewById(R.id.removePizza).setOnClickListener(v -> removePizza());
    }
    private void removePizza(){
        if (Order.getInstance().getPizzas().isEmpty()) {
            //showRemoveButEmptyPopup();
        } else {
            ListView listOfPizzas = getView().findViewById(R.id.currentOrderView);
            if (selectedPosition != ILLEGAL_INDEX) {
                Pizza selectedPizza = Order.getInstance().getPizzas().get(selectedPosition);
                Order.getInstance().removePizza(selectedPizza);
                updateCurrentOrderView();
            //    showRemovedPopup();
            }
        }
    }
    private void placeOrder() {
        ListView listOfPizzas = getView().findViewById(R.id.currentOrderView);
        if (!order.getPizzas().isEmpty() && listOfPizzas.getCount() == order.getPizzas().size()) {
            //showAddedPopup();
            updateCurrentOrderView();
            storeOrders.addOrder(Order.getInstance());
            Order.getInstance().resetOrder();

            updateCurrentOrderView();
        } else {
            //showEmptyPopup();
        }
    }
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
}