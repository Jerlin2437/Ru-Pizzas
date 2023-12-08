package com.example.softmeth4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.businesslogic.StoreOrders;

import java.util.ArrayList;
import java.util.List;

public class fragment_store_orders extends Fragment {
    private StoreOrders storeOrders;
    private Order order;



    public fragment_store_orders() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeOrders = StoreOrders.getInstance();
        order = Order.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store_orders, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spinner orderSpinner = view.findViewById(R.id.currentOrderView);
        if (orderSpinner != null) {
            populateSpinner();
        } else {
            // Log an error or handle appropriately if the Spinner is null
        }
    }
    private void populateSpinner(){
        Spinner orderSpinner = getView().findViewById(R.id.currentOrderView);
        List<String> orders= new ArrayList<String>();
        for (int x = 0; x < StoreOrders.getInstance().getTotalOrders(); x++){
            orders.add(String.valueOf(storeOrders.getOrders().get(x).getOrderNumber()));
        }

        ArrayAdapter<String> orderArrayAdapter = new ArrayAdapter<>(getView().getContext(),
                android.R.layout.simple_spinner_item, orders);
        orderArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderSpinner.setAdapter(orderArrayAdapter);
    }
}