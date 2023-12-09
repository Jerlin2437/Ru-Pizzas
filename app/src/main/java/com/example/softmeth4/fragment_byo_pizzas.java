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
        view = inflater.inflate(R.layout.fragment_byo_pizzas, container, false);
        setupAllViews();

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updatePizzaPrice();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //if nothing is done, small is the default selection
                sizeSpinner.setSelection(0);
                updatePizzaPrice();
            }
        });

        notSelectedToppingsList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTopping = notSelectedToppingsAdapter.getItem(position);
            addTopping(selectedTopping);
            updatePizzaPrice();
        });

        selectedToppingsList.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTopping = selectedToppingsAdapter.getItem(position);
            removeTopping(selectedTopping);
            updatePizzaPrice();
        });

//        sauceRadioGroup.setOnCheckedChangeListener((group, checkedId) -> updatePizzaPrice());
        buildExtraSauceButton.setOnCheckedChangeListener((buttonView, isChecked) -> updatePizzaPrice());
        buildExtraCheeseButton.setOnCheckedChangeListener((buttonView, isChecked) -> updatePizzaPrice());
        buildAddToOrder.setOnClickListener(new BuildAddToOrderClickListener());

        RadioButton defaultSauceRadioButton = view.findViewById(R.id.buildTomato);
        defaultSauceRadioButton.setChecked(true);

        return view;
    }

    private void setupAllViews(){
        //set up spinner
        sizeSpinner = view.findViewById(R.id.buildSpinner);
        spinnerAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_item, sizeList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(spinnerAdapter);

        //setup topping list views
        notSelectedToppingsList = (ListView) view.findViewById(R.id.notSelectedToppings);
        selectedToppingsList = (ListView) view.findViewById(R.id.selectedToppings);

        notSelectedToppingsAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, new ArrayList<>(toppingsList));
        notSelectedToppingsList.setAdapter(notSelectedToppingsAdapter);

        selectedToppingsAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, emptyList);
        selectedToppingsList.setAdapter(selectedToppingsAdapter);

        //set up buttons and buildPrice text view
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
            String noSpaceTopping = selectedTopping.replace(" ", "_");
//            selectedToppingsAdapter.add(displayText(noSpaceTopping));
            selectedToppingsAdapter.add(noSpaceTopping);
            selectedToppingsAdapter.notifyDataSetChanged();
            notSelectedToppingsAdapter.remove(selectedTopping);
            toppingCount++;
            updatePizzaPrice();
        }
    }

    private void removeTopping(String selectedTopping){
        String noSpaceTopping = selectedTopping.replace(" ", "_");
//        notSelectedToppingsAdapter.add(displayText(noSpaceTopping));
        notSelectedToppingsAdapter.add(noSpaceTopping);
        notSelectedToppingsAdapter.notifyDataSetChanged();
        selectedToppingsAdapter.remove(selectedTopping);
        toppingCount--;
        updatePizzaPrice();
    }

//    private String displayText(String topping){
//        return topping.replace("_"," ");
//    }

    private void updatePizzaPrice() {
        if (sizeSpinner.getSelectedItem() != null) {
            pizza = pizzaParse();
            if (pizza != null) {
                double basePrice = pizza.price();

                // Calculate additional topping price only if more than 3 toppings are selected
                additionalToppingPrice = Math.max(0, toppingCount - MIN_TOPPING) * TOPPING_PRICE;

                // Add extra sauce/cheese price if selected
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
    }

    private Pizza pizzaParse() {
        String pizzaType = "BYO";
        String size = sizeSpinner.getSelectedItem().toString();
        String extraSauceCheck = "false";
        String extraCheeseCheck = "false";
        int selectedRadioButtonId = sauceRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = view.findViewById(selectedRadioButtonId);
        String selectedSauce = selectedRadioButton.getText().toString();

        if (buildExtraSauceButton.isChecked()){
            extraSauceCheck = "true";
        }
        if (buildExtraCheeseButton.isChecked()){
            extraCheeseCheck = "true";
        }

        StringBuilder allToppings = new StringBuilder();
        for (int i = 0; i < selectedToppingsList.getCount(); i++){
            String topping = (String) selectedToppingsList.getItemAtPosition(i);
            allToppings.append(topping);
            if (i < selectedToppingsList.getCount() - 1){
                allToppings.append(" ");
            }
        }

        return PizzaMaker.createPizza(pizzaType + " " + size + " " + extraSauceCheck + " " + extraCheeseCheck + " " +
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
                    if (pizza != null) {
                        if (buildExtraCheeseButton.isChecked()) {
                            pizza.setExtraCheese(true);
                        }
                        if (buildExtraSauceButton.isChecked()) {
                            pizza.setExtraSauce(true);
                        }
                    }
                    order.addPizza(pizza);
                    showSuccessPopup();
                } else {
                    showFailurePopup1();
                }
//            } else {
//                showFailurePopup2();
//            }
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

//        private void showFailurePopup2() {
//            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//            builder.setTitle("Pizza Order Unsuccessful")
//                    .setMessage("Please select a pizza size.")
//                    .setPositiveButton("OK", null)
//                    .show();
//        }
    }

    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

}
