package com.example.softmeth4;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.businesslogic.PizzaMaker;
import com.example.softmeth4.pizzas.Pizza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the factory method to
 * create an instance of this fragment.
 */
public class fragment_byo_pizzas extends Fragment {
    private static final int MIN_TOPPING = 3;
    private static final int MAX_TOPPING = 7;
    private static final double TOPPING_PRICE = 1.49;
    private static final double MORESAUCECHEESE = 1.0;
    private List<String> pizzaList;
    private Order order;
    private Pizza pizza;
    private View view;

    private CheckBox buildExtraCheeseButton;
    private CheckBox buildExtraSauceButton;
    private TextView buildPrice;
    private Button buildAddToOrder;
    private RadioGroup sauceRadioGroup;

    private Spinner sizeSpinner;
    private ListView notSelectedToppingsList;
    private ListView selectedToppingsList;
    private ArrayAdapter<String> spinnerAdapter;
    private ArrayAdapter<String> notSelectedToppingsAdapter;
    private ArrayAdapter<String> selectedToppingsAdapter;
    private List<String> sizeList = Arrays.asList("Small", "Medium", "Large");
    private List<String> toppingsList = Arrays.asList("Sausage", "Beef", "Pepperoni", "Ham", "Onion", "Green Pepper",
          "Mushroom", "Black Olive", "Shrimp", "Squid", "Crab Meat", "Spam", "Fish");
    private List<String> emptyList = new ArrayList<>();

    private int toppingCount;
    private double additionalToppingPrice;

    public fragment_byo_pizzas() {
        this.pizza = null;
        this.order = Order.getInstance();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = Order.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_byo_pizzas, container, false);
        setupSizeSpinner();
        setupToppingsListViews();
        setupButtonsEtc();

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updatePizzaPrice();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //if nothing is done, small is the default selection
                updatePizzaPrice();
            }
        });

        notSelectedToppingsList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTopping = notSelectedToppingsAdapter.getItem(position);
            addTopping(selectedTopping);
        });

        selectedToppingsList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTopping = selectedToppingsAdapter.getItem(position);
            removeTopping(selectedTopping);
        });

        sauceRadioGroup.setOnCheckedChangeListener((group, checkedId) -> updatePizzaPrice());
        buildExtraSauceButton.setOnCheckedChangeListener((buttonView, isChecked) -> updatePizzaPrice());
        buildExtraCheeseButton.setOnCheckedChangeListener((buttonView, isChecked) -> updatePizzaPrice());
        buildAddToOrder.setOnClickListener(new BuildAddToOrderClickListener());

        return view;
    }

    private void setupSizeSpinner(){
        sizeSpinner = view.findViewById(R.id.buildSpinner);
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sizeList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(spinnerAdapter);
    }

    private void setupToppingsListViews(){
        notSelectedToppingsList = (ListView)view.findViewById(R.id.notSelectedToppings);
        selectedToppingsList = (ListView) view.findViewById(R.id.selectedToppings);

        notSelectedToppingsAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, new ArrayList<>(toppingsList));
        notSelectedToppingsList.setAdapter(notSelectedToppingsAdapter);

        selectedToppingsAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, emptyList);
        selectedToppingsList.setAdapter(selectedToppingsAdapter);
    }

    private void setupButtonsEtc(){
        buildExtraCheeseButton = view.findViewById(R.id.buildExtraCheese);
        buildExtraSauceButton = view.findViewById(R.id.buildExtraSauce);
        buildPrice = view.findViewById(R.id.buildPriceTextView);
        buildAddToOrder = view.findViewById(R.id.buildAddToOrder);
        sauceRadioGroup = view.findViewById(R.id.sauceRadioGroup);
    }

    private void addTopping(String selectedTopping){
        if (selectedToppingsAdapter.getCount() >= MAX_TOPPING){
            showAlert("Too many toppings!", "You can only have a maximum of 7 toppings!");
        } else{
            selectedToppingsAdapter.add(selectedTopping);
            selectedToppingsAdapter.notifyDataSetChanged();
            notSelectedToppingsAdapter.remove(selectedTopping);
            toppingCount++;
            updatePizzaPrice();
        }
    }

    private void removeTopping(String selectedTopping){
        notSelectedToppingsAdapter.add(selectedTopping);
        notSelectedToppingsAdapter.notifyDataSetChanged();
        selectedToppingsAdapter.remove(selectedTopping);
        toppingCount--;
        updatePizzaPrice();
    }

    private void updatePizzaPrice() {
        if (pizza != null) {
            pizza = pizzaParse();
            //base price without toppings
            double basePrice = pizza.price();
            //if more than 3 toppings, add $1.49 for each additional topping after 3
            additionalToppingPrice = Math.max(0, toppingCount - MAX_TOPPING) * TOPPING_PRICE;

            if (buildExtraSauceButton.isChecked()) {
                additionalToppingPrice += MORESAUCECHEESE;
            }

            if (buildExtraCheeseButton.isChecked()) {
                additionalToppingPrice += MORESAUCECHEESE;
            }

            double totalPrice = basePrice + additionalToppingPrice;
            String formattedValue = String.format("%.2f", totalPrice);
            buildPrice.setText(formattedValue);
        }
    }

    private Pizza pizzaParse() {
        String size = sizeSpinner.getSelectedItem().toString();
        String pizzaType = "BYO";
        int selectedRadioButtonId = sauceRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = view.findViewById(selectedRadioButtonId);
        String selectedSauce = selectedRadioButton.getText().toString();
//        String extraSauceCheck = "false";
//        String extraCheeseCheck = "false";
//        if (buildExtraSauceButton.isChecked()){
//            extraSauceCheck = "true";
//        }
//        if (buildExtraCheeseButton.isChecked()){
//            extraCheeseCheck = "true";
//        }

        StringBuilder allToppings = new StringBuilder();
        for (int i = 0; i < selectedToppingsAdapter.getCount(); i++) {
            allToppings.append(selectedToppingsAdapter.getItem(i)).append(" ");
        }

//        return PizzaMaker.createPizza(pizzaType + " " + size + " " + extraSauceCheck + " " +
//                extraCheeseCheck + " " + selectedSauce + " " + allToppings.toString().trim());
        return PizzaMaker.createPizza(pizzaType + " " + size + " false false " +
                selectedSauce + " " + allToppings.toString().trim());
    }

    private class BuildAddToOrderClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            addToOrder();
        }

        private void addToOrder() {
            if (sizeSpinner.getSelectedItem() != null) {
                if (toppingCount >= MIN_TOPPING) {
                    pizza = pizzaParse();
                    if (buildExtraCheeseButton.isChecked()) {
                        pizza.setExtraCheese(true);
                    }
                    if (buildExtraSauceButton.isChecked()) {
                        pizza.setExtraSauce(true);
                    }
                    order.addPizza(pizza);
                    showSuccessPopup();
                } else {
                    showFailurePopup1();
                }
            } else {
                showFailurePopup2();
            }
        }

        private void showSuccessPopup() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Pizza Order Successful")
                    .setMessage("Your pizza order has been added successfully!")
                    .setPositiveButton("OK", null)
                    .show();
        }

        private void showFailurePopup1() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Pizza Order Unsuccessful")
                    .setMessage("You need at least 3 toppings.")
                    .setPositiveButton("OK", null)
                    .show();
        }

        private void showFailurePopup2() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Pizza Order Unsuccessful")
                    .setMessage("Please select a pizza size.")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

}
