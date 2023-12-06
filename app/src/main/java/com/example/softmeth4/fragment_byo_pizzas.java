package com.example.softmeth4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.softmeth4.businesslogic.Order;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class fragment_byo_pizzas extends Fragment {

  private Order order;
  private Spinner spinner;
  private ListView toppingsListView;
  private ArrayAdapter<String> spinnerAdapter;
  private ArrayAdapter<String> toppingsAdapter;
  private List<String> sizeList = Arrays.asList("Small", "Medium", "Large");
  private List<String> toppingsList = Arrays.asList("Sausage", "Beef", "Pepperoni", "Ham", "Onion", "Green Pepper",
          "Mushroom", "Black Olive", "Shrimp", "Squid", "Crab Meat", "Spam", "Fish");

    public fragment_byo_pizzas() {
        // Required empty public constructor
    }

//    public static fragment_byo_pizzas newInstance(String param1, String param2) {
//        fragment_byo_pizzas fragment = new fragment_byo_pizzas();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = Order.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_byo_pizzas, container, false);
        spinner = view.findViewById(R.id.buildSpinner);
        // Initialize ArrayAdapter
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sizeList);
        // Set the dropdown layout style
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter to the Spinner
        spinner.setAdapter(spinnerAdapter);

        toppingsListView = view.findViewById(R.id.notSelectedToppings);
        toppingsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, toppingsList);
        toppingsListView.setAdapter(toppingsAdapter);

        return view;
    }
}