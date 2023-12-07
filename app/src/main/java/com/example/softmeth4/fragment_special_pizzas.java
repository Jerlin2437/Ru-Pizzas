package com.example.softmeth4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.businesslogic.PizzaMaker;
import com.example.softmeth4.pizzas.Pizza;

import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class fragment_special_pizzas extends Fragment {
    private Order order;
    private Pizza pizza;
    private Spinner spinner;
    private ArrayAdapter<String> spinnerAdapter;
    private List<String> pizzaList = Arrays.asList("Cheese", "Chinese", "Deluxe", "Korean", "Meatzza", "Pepperoni",
            "Proteen", "Seafood", "Spham", "Supreme");

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
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pizzaList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());

        // Inflate the layout for this fragment
        return view;
    }
    private void updatePizzaPrice(){
        if (pizza != null) {
            pizza = pizzaParse();
            //base price without toppings
            double basePrice = pizza.price();
            //resets to 0
//            extraToppingsPrice = 0.0;
//            if (extraSauce.isSelected()) {
//                extraToppingsPrice += MORESAUCECHEESE;
//            }
//
//            if (extraCheese.isSelected()) {
//                extraToppingsPrice += MORESAUCECHEESE;
//            }

          //  double totalPrice = basePrice + extraToppingsPrice;


            String formattedValue = String.format("%.2f", basePrice);
            TextView priceField = getView().findViewById(R.id.price);
            priceField.setText(formattedValue);
        }
    }
    private Pizza pizzaParse() {
        String selectedPizza = (String) spinner.getSelectedItem();
        RadioGroup pizzaSizesRadioGroup = getView().findViewById(R.id.specialtyRadioButtonGroup); // Use getView() to get the fragment's root view
        int selectedRadioButtonId = pizzaSizesRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = getView().findViewById(selectedRadioButtonId);
        String size = selectedRadioButton.getText().toString();


        return PizzaMaker.createPizza(selectedPizza + " " + size + " false false");
    }
    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            pizza = pizzaParse();
            updatePizzaPrice();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // Do nothing here
        }
    }




}