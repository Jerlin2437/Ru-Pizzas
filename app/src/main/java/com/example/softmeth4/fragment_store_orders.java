package com.example.softmeth4;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.businesslogic.StoreOrders;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a fragment class that serves as a menu for displaying a list of store orders in the pizza app.
 * The class provides a way to view, select, and remove store orders.
 *
 * @author Jason Lei, Jerlin Yuen
 */

public class fragment_store_orders extends Fragment {
    private StoreOrders storeOrders;
    private Order order;
    private Spinner orderSpinner;
    private TextView finalOrderTotal;
    private ListView displayStoreOrders;

    /**
     * Default empty constructor
     */
    public fragment_store_orders() {
        // Required empty public constructor
    }

    /**
     * General required Android fragment onCreate method called upon creation
     *
     * @param savedInstanceState saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeOrders = StoreOrders.getInstance();
        order = Order.getInstance();
        Log.d("Handle!", storeOrders.toString());
    }

    /**
     * General required Android fragment onCreate method called upon creation.
     *
     * @param inflater           fragment_store_orders
     * @param container          container
     * @param savedInstanceState saved state
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_orders, container, false);

        return view;
    }

    /**
     * This method is called after the view has been created, initializing UI components,
     * setting up a spinner, and defining a click listener for the cancel order button.
     *
     * @param view               display view layout
     * @param savedInstanceState saved state
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        orderSpinner = view.findViewById(R.id.allOrders);
        finalOrderTotal = view.findViewById(R.id.orderTotalCost);
        displayStoreOrders = view.findViewById(R.id.displayStoreOrders);

        populateSpinner();

        if (orderSpinner != null) {
            orderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    displaySelectedOrder();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // if nothing is done, small is the default selection
                    orderSpinner.setSelection(0);
                    displaySelectedOrder();
                }
            });
        }
        view.findViewById(R.id.cancelOrder).setOnClickListener(v -> removeOrder());
    }

    /**
     * This method populates the spinner with order numbers if there are orders in 'StoreOrders'
     */
    private void populateSpinner() {
        if (StoreOrders.getInstance().getTotalOrders() != 0) {
            Spinner orderSpinner = getView().findViewById(R.id.allOrders);
            List<String> orders = new ArrayList<>();
            for (int x = 0; x < StoreOrders.getInstance().getTotalOrders(); x++) {
                orders.add(String.valueOf(StoreOrders.getInstance().getOrders().get(x).getOrderNumber()));
            }

            ArrayAdapter<String> orderArrayAdapter = new ArrayAdapter<>(getView().getContext(),
                    android.R.layout.simple_spinner_item, orders);
            orderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            orderSpinner.setAdapter(orderArrayAdapter);
        }
    }

    /**
     * This method displays the details of the selected order, including the order summary
     * and total cost.
     */
    private void displaySelectedOrder() {
        // Clears listview first of previous items/orders
        displayStoreOrders.setAdapter(null);

        if (orderSpinner != null && orderSpinner.getSelectedItem() != null) {
            int orderNumber = Integer.parseInt(orderSpinner.getSelectedItem().toString());
            Order selectedOrder = storeOrders.getOrderByNumber(orderNumber);
            if (selectedOrder != null) {
                List<String> orderDetails = new ArrayList<>();
                orderDetails.add(selectedOrder.toFinalOrderDetailsString());

                ArrayAdapter<String> orderDetailsAdapter = new ArrayAdapter<>(
                        requireContext(), android.R.layout.simple_list_item_1, orderDetails);

                displayStoreOrders.setAdapter(orderDetailsAdapter);

                finalOrderTotal.setText(String.format("%.2f", selectedOrder.getOrderTotalValue()));
            }
        }
    }

    /**
     * This method removes the selected order and updates the spinner accordingly, handling success,
     * error and warning messages when necessary.
     */
    private void removeOrder() {
        Spinner orderSpinner = getView().findViewById(R.id.allOrders);
        if (orderSpinner.getSelectedItem() != null) {
            int orderId = Integer.parseInt(orderSpinner.getSelectedItem().toString());
            if (storeOrders.removeOrder(orderId)) {
                showSuccessMessage();
                ArrayAdapter<String> adapter = (ArrayAdapter<String>) orderSpinner.getAdapter();
                adapter.remove(orderSpinner.getSelectedItem().toString());
                adapter.notifyDataSetChanged();
                finalOrderTotal.setText("0.00");
            } else {
                showErrorMessage();
            }
        } else {
            showWarningMessage();
        }
    }

    /**
     * Displays a specific success message if an order is successfully removed from store orders
     */
    private void showSuccessMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Success")
                .setMessage("Order successfully removed.")
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * Displays a specific error message if order is not able to be removed from store orders
     */
    private void showErrorMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Error")
                .setMessage("Failed to remove the order. Please try again.")
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * Displays a specific warning message if an order is not selected
     */
    private void showWarningMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Warning")
                .setMessage("Please select an order to remove.")
                .setPositiveButton("OK", null)
                .show();
    }
}