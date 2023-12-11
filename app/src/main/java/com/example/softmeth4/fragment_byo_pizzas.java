package com.example.softmeth4;

import android.app.AlertDialog;
import android.os.Bundle;
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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.businesslogic.PizzaMaker;
import com.example.softmeth4.pizzas.Pizza;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is a fragment class that serves as a menu for building custom pizzas in the pizza app.
 * The class contains UI components such as checkboxes, spinners, and listviews the user can interact with
 * to choose toppings, as well as methods to calculate and display the price of the custom pizza based on selected
 * options, and display alert dialogs and toast message when necessary.
 *
 * @author Jason Lei, Jerlin Yuen
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
    private final List<String> sizeList = Arrays.asList("Small", "Medium", "Large");
    private final List<String> toppingsList = Arrays.asList("Sausage", "Beef", "Pepperoni", "Ham", "Onion", "Green Pepper",
            "Mushroom", "Black Olive", "Shrimp", "Squid", "Crab Meat", "Spam", "Fish");
    private final List<String> emptyList = new ArrayList<>();

    private int toppingCount;
    private double additionalToppingPrice;

    /**
     * Default empty constructor
     */
    public fragment_byo_pizzas() {
        // Required empty public constructor
    }

    /**
     * General required Android fragment onCreate method called upon creation
     * Initializes the 'order' variable by getting an instance of the 'Order' class
     *
     * @param savedInstanceState saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = Order.getInstance();
    }

    /**
     * Creates and returns the build your own pizza fragment's view.
     * Inflates the layout defined in 'fragment_byo_pizzas.xml', sets up various views
     * (buttons, checkboxes, list views) by calling the setupAllViews method, as well as setting up
     * event listeners for button clicks/checkbox clicks, etc.
     *
     * @param inflater           fragment xml class
     * @param container          container
     * @param savedInstanceState saved state
     * @return fragment_byo_pizzas layout view
     */
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

    /**
     * Initializes various UI components (spinners, list views, and buttons) used in the fragment
     */
    private void setupAllViews() {
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

    /**
     * Adds a selected topping to the list of toppings for a custom pizza
     * Checks for the maximum topping limit and updates the UI when a topping is selected/added.
     */
    private void addTopping(String selectedTopping) {
        if (selectedToppingsAdapter.getCount() >= MAX_TOPPING) {
            showAlert("Too many toppings!", "You can only have a maximum of 7 toppings!");
        } else {
            String noSpaceTopping = selectedTopping.replace(" ", "_");
//            selectedToppingsAdapter.add(displayText(noSpaceTopping));
            selectedToppingsAdapter.add(noSpaceTopping);
            selectedToppingsAdapter.notifyDataSetChanged();
            notSelectedToppingsAdapter.remove(selectedTopping);
            toppingCount++;
            updatePizzaPrice();
        }
    }


    /**
     * Removes a selected topping from the list of toppings for a custom pizza
     * and updates the UI when a topping is selected/removed.
     */
    private void removeTopping(String selectedTopping) {
        String noSpaceTopping = selectedTopping.replace(" ", "_");
//        notSelectedToppingsAdapter.add(displayText(noSpaceTopping));
        notSelectedToppingsAdapter.add(noSpaceTopping);
        notSelectedToppingsAdapter.notifyDataSetChanged();
        selectedToppingsAdapter.remove(selectedTopping);
        toppingCount--;
        updatePizzaPrice();
    }

    /**
     * Updates the display text view buildPrice, representing the subtotal price for the pizza.
     */
    private void updatePizzaPrice() {
        if (sizeSpinner.getSelectedItem() != null) {
            pizza = pizzaParse();
            if (pizza != null) {
                double basePrice = pizza.price();


//                // Add extra sauce/cheese price if selected, not sure if needed, it adds x2 MORESAUCECHEESE to display
//                if (buildExtraSauceButton.isChecked()) {
//                    additionalToppingPrice += MORESAUCECHEESE;
//                }
//
//                if (buildExtraCheeseButton.isChecked()) {
//                    additionalToppingPrice += MORESAUCECHEESE;
//                }

                double totalPrice = basePrice;
                String formattedValue = String.format("%.2f", totalPrice);
                buildPrice.setText(formattedValue);
            }
        }
    }


    /**
     * Parses the user's selected options and creates a new 'Pizza' object based on user selections
     * <p>
     * return a new 'Pizza' object based on selected toppings/user selections
     */
    private Pizza pizzaParse() {
        String pizzaType = "BYO";
        String size = sizeSpinner.getSelectedItem().toString();
        String extraSauceCheck = "false";
        String extraCheeseCheck = "false";
        int selectedRadioButtonId = sauceRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = view.findViewById(selectedRadioButtonId);
        String selectedSauce = selectedRadioButton.getText().toString();

        if (buildExtraSauceButton.isChecked()) {
            extraSauceCheck = "true";
        }
        if (buildExtraCheeseButton.isChecked()) {
            extraCheeseCheck = "true";
        }

        StringBuilder allToppings = new StringBuilder();
        for (int i = 0; i < selectedToppingsList.getCount(); i++) {
            String topping = (String) selectedToppingsList.getItemAtPosition(i);
            allToppings.append(topping);
            if (i < selectedToppingsList.getCount() - 1) {
                allToppings.append(" ");
            }
        }

        return PizzaMaker.createPizza(pizzaType + " " + size + " " + extraSauceCheck + " " + extraCheeseCheck + " " +
                selectedSauce + " " + allToppings.toString().trim());
    }

    /**
     * An inner class that handles the action event triggered by the buildAddToOrder button
     */
    private class BuildAddToOrderClickListener implements View.OnClickListener {
        /**
         * Event handler that calls the method addToOrder() when the action event
         * is triggered (button is pressed)
         *
         * @param v view
         */
        @Override
        public void onClick(View v) {
            addToOrder();
        }

        /**
         * Adds the BYO pizza to the order, displaying a success popup if successful,
         * and a failure popup if it does not meet requirements (ex: pizza does not have at least 3 toppings)
         */
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

        /**
         * Displays a success alert dialog/popup with a specific message
         */
        private void showSuccessPopup() {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("Pizza Order Successful")
                    .setMessage("Your pizza order has been added successfully!")
                    .setPositiveButton("OK", null)
                    .show();
        }

        /**
         * Displays a failure alert/popup/toast with a specific message if the order does not meet
         * topping requirements
         */
        private void showFailurePopup1() {
            showToast("Pizza Order Unsuccessful - You need at least 3 toppings.");
        }
    }

    /**
     * A general alert dialog message formatted method to display a specific message
     */
    private void showAlert(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    /**
     * A general toast message formatted method to display a specific message
     */
    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}
