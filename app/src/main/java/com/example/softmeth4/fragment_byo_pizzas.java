package com.example.softmeth4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.businesslogic.PizzaMaker;
import com.example.softmeth4.enums.Topping;
import com.example.softmeth4.pizzas.Pizza;

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
  private Order order;
  private View view;

  private CheckBox buildExtraCheese;
  private CheckBox buildExtraSauce;
  private EditText buildPrice;
  private Button addTopping;
  private Button removeTopping;
  private Button buildAddToOrder;
  private RadioGroup sauceRadioGroup;

  private Spinner sizeSpinner;
  private ListView notSelectedToppings;
  private ListView selectedToppings;
  private ArrayAdapter<String> spinnerAdapter;
  private ArrayAdapter<String> notSelectedToppingsAdapter;
  private ArrayAdapter<String> selectedToppingsAdapter;
  private List<String> sizeList = Arrays.asList("Small", "Medium", "Large");
  private List<String> toppingsList = Arrays.asList("Sausage", "Beef", "Pepperoni", "Ham", "Onion", "Green Pepper",
          "Mushroom", "Black Olive", "Shrimp", "Squid", "Crab Meat", "Spam", "Fish");

    private Pizza pizza;
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

        setupSizeSpinner();
        setupToppingsListViews();

        buildExtraCheese = view.findViewById(R.id.buildExtraCheese);
        buildExtraSauce = view.findViewById(R.id.buildExtraSauce);
        buildPrice = view.findViewById(R.id.buildPrice);
        addTopping = view.findViewById(R.id.addTopping);
        removeTopping = view.findViewById(R.id.removeTopping);
        buildAddToOrder = view.findViewById(R.id.buildAddToOrder);
        sauceRadioGroup = view.findViewById(R.id.sauceRadioGroup);

        addTopping.setOnClickListener(v -> {
            addToppings();
            pizza = pizzaParse();
            updatePizzaPrice();
        });

        removeTopping.setOnClickListener(v -> {
            removeToppings();
            pizza = pizzaParse();
            updatePizzaPrice();
        });

        buildExtraSauce.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pizza = pizzaParse();
            updatePizzaPrice();
        });

        buildExtraCheese.setOnCheckedChangeListener((buttonView, isChecked) -> {
            pizza = pizzaParse();
            updatePizzaPrice();
        });

        buildAddToOrder.setOnClickListener(v -> addToOrder());

        return view;
    }

    private void setupSizeSpinner(){
        sizeSpinner = view.findViewById(R.id.buildSpinner);
        // Initialize ArrayAdapter
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, sizeList);
        // Set the dropdown layout style
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the adapter to the Spinner
        sizeSpinner.setAdapter(spinnerAdapter);
    }

    private void setupToppingsListViews(){
        notSelectedToppings = view.findViewById(R.id.notSelectedToppings);
        notSelectedToppingsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_multiple_choice, toppingsList);
        notSelectedToppings.setAdapter(notSelectedToppingsAdapter);
        notSelectedToppings.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        selectedToppings = view.findViewById(R.id.selectedToppings);
        selectedToppingsAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1);
        selectedToppings.setAdapter(selectedToppingsAdapter);
        selectedToppings.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    private void updatePizzaPrice() {
        if (pizza != null) {
            if (toppingCount >= MIN_TOPPING) {
                pizza = pizzaParse();
                //base price without toppings
                double basePrice = pizza.price();
                //if more than 3 toppings, add $1.49 for each additional topping after 3
                additionalToppingPrice = Math.max(0, toppingCount - MAX_TOPPING) * TOPPING_PRICE;

                if (buildExtraSauce.isChecked()) {
                    additionalToppingPrice += MORESAUCECHEESE;
                }

                if (buildExtraCheese.isChecked()) {
                    additionalToppingPrice += MORESAUCECHEESE;
                }

                double totalPrice = basePrice + additionalToppingPrice;
                String formattedValue = String.format("%.2f", totalPrice);
                buildPrice.setText(formattedValue);
            } else {
                double totalPrice = pizza.price() + additionalToppingPrice;
                String formattedValue = String.format("%.2f", totalPrice);
                buildPrice.setText(formattedValue);
            }
        }
    }

    private Pizza pizzaParse() {
        RadioButton selectedRadioButton = view.findViewById(sauceRadioGroup.getCheckedRadioButtonId());
        String sauce = selectedRadioButton.getText().toString();
        String size = sizeSpinner.getSelectedItem().toString();
        String pizzaType = "BYO";

        SparseBooleanArray checkedToppings = selectedToppings.getCheckedItemPositions();
        StringBuilder allToppings = new StringBuilder();

        for (int i = 0; i < checkedToppings.size(); i++) {
            if (checkedToppings.valueAt(i)) {
                allToppings.append(notSelectedToppingsAdapter.getItem(checkedToppings.keyAt(i))).append(" ");
            }
        }
        return PizzaMaker.createPizza(pizzaType + " " + size + " false false " + sauce + " " + allToppings.toString().trim());
    }

    private void addToppings() {
        SparseBooleanArray checkedItems = notSelectedToppings.getCheckedItemPositions();

        if (checkedItems.size() >= MAX_TOPPING) {
            showAlert("Too many toppings!", "You can only have a max of 7 toppings!");
        } else {
            // Loop through checked items
            for (int i = checkedItems.size() - 1; i >= 0; i--) {
                int position = checkedItems.keyAt(i);
                if (checkedItems.valueAt(i)) {
                    String selectedTopping = notSelectedToppingsAdapter.getItem(position);
                    // Move the topping from notSelectedToppings to selectedToppings
                    notSelectedToppingsAdapter.remove(selectedTopping);
                    selectedToppingsAdapter.add(selectedTopping);
                    toppingCount++; // Update topping count
                }
            }
            // Clear the checked state after moving toppings
            notSelectedToppings.clearChoices();
            updatePizzaPrice();
        }
    }

    private void removeToppings() {
        SparseBooleanArray checkedItems = selectedToppings.getCheckedItemPositions();

        // Loop through checked items
        for (int i = checkedItems.size() - 1; i >= 0; i--) {
            int position = checkedItems.keyAt(i);
            if (checkedItems.valueAt(i)) {
                String selectedTopping = selectedToppingsAdapter.getItem(position);
                // Move the topping from selectedToppings to notSelectedToppings
                selectedToppingsAdapter.remove(selectedTopping);
                notSelectedToppingsAdapter.add(selectedTopping);
                toppingCount--; // Update topping count
            }
        }
        // Clear the checked state after moving toppings
        selectedToppings.clearChoices();
        updatePizzaPrice();
    }

    private void addToOrder() {
        if (sizeSpinner.getSelectedItem() != null) {
            if (toppingCount >= MIN_TOPPING) {
                pizza = pizzaParse();
                if (buildExtraCheese.isChecked()) {
                    pizza.setExtraCheese(true);
                }
                if (buildExtraSauce.isChecked()) {
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


    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }




}