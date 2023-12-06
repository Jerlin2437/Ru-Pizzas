package com.example.softmeth4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.softmeth4.businesslogic.Order;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class fragment_special_pizzas extends Fragment {
    private Order order;

    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> pizzaList = Arrays.asList("Cheese", "Chinese", "Deluxe", "Korean", "Meatzza", "Pepperoni" +
            "Proteen" + "Seafood" + "Spham" + "Supreme");

    public fragment_special_pizzas() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = Order.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_pizzas, container, false);

        spinner = view.findViewById(R.id.chooseSpecialty);

        // Initialize ArrayAdapter
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pizzaList);

        // Set the dropdown layout style
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        spinner.setAdapter(spinnerAdapter);


        // Inflate the layout for this fragment
        return view;
    }
}