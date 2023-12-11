package com.example.softmeth4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.businesslogic.PizzaMaker;
import com.example.softmeth4.enums.Topping;
import com.example.softmeth4.pizzas.Pizza;

import java.util.Arrays;
import java.util.List;

/**
 * This class is a custom RecyclerView adapter used to populate a RecyclerView with a list
 * of specialty pizzas in the pizza app. It provides a UI for customizing and adding
 * the specialty pizzas to the current order.
 *
 * @author Jerlin Yuen
 */

public class SpecialPizzaAdapter extends RecyclerView.Adapter<SpecialPizzaAdapter.ViewHolder> {
    private Pizza pizza;
    private final Order order;
    private final List<String> pizzaList;
    private final List<String> pizzaPicture;


    /**
     * Parameterized constructor, initializing the adapter with a list of pizza names,
     * corresponding pizza pictures, a reference to the current 'Order' instance, and to the current
     * 'Pizza' instance
     *
     * @param pizzaList    list of pizzas
     * @param pizzaPicture associated pizza picture
     */
    public SpecialPizzaAdapter(List<String> pizzaList, List<String> pizzaPicture) {
        this.pizzaList = pizzaList;
        this.pizza = null;
        this.order = Order.getInstance();
        this.pizzaPicture = pizzaPicture;
    }

    /**
     * This method inflates the layout for each item in the RecyclerView, returning a new
     * instance of the 'Viewholder' class
     *
     * @param parent   parent
     * @param viewType type of view
     * @return new instance of ViewHolder class
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_special_pizza, parent, false);
        return new ViewHolder(view);
    }

    /**
     * This method binds data to the views within each ViewHolder, setting the pizza name
     * initializing views, setting listeners, and handling the addition of the pizza to the order
     * when "Add to Order" button is clicked.
     *
     * @param holder   holder
     * @param position position of pizza in the order
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String pizzaName = pizzaList.get(position);

        // Set data to views in the ViewHolder based on pizzaName
        holder.pizzaName.setText(pizzaName);

        // Initialize views and set listeners
        holder.checkBoxExtraSauce.setOnCheckedChangeListener((buttonView, isChecked) -> updatePrice(holder));
        holder.checkBoxExtraCheese.setOnCheckedChangeListener((buttonView, isChecked) -> updatePrice(holder));
        holder.sizeGroup.setOnCheckedChangeListener((group, checkedId) -> updatePrice(holder));

        holder.addToOrderButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantitySpinner.getSelectedItem().toString());
            pizza = pizzaParse(holder);
            updatePrice(holder);
            for (int x = 0; x < quantity; x++)
                order.addPizza(pizza);
            //       create popup that says pizza added to order
            showToast(holder, "Your pizza order has been added successfully!");
        });
        String imageName = pizzaPicture.get(position);
        holder.bind(imageName);
        pizza = pizzaParse(holder);

        holder.toppings.setText(pizza.toppingsToString());
        updatePrice(holder);
    }

    /**
     * A general toast message formatted method to display a specific message
     */
    private void showToast(@NonNull ViewHolder holder, String message) {
        Toast.makeText(holder.itemView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Getter method (accessor method)
     *
     * @return total number of items in the pizza list
     */
    @Override
    public int getItemCount() {
        return pizzaList.size();
    }


    /**
     * Calculates and updates the displayed price based on user selections
     *
     * @param holder holder
     */
    private void updatePrice(@NonNull ViewHolder holder) {
        if (pizza != null) {
            pizza = pizzaParse(holder);
            //base price without toppings
            double basePrice = pizza.price();

            String formattedValue = String.format("%.2f", basePrice);
            holder.priceTextView.setText(formattedValue);
        }
    }

    /**
     * This method parses a user's selected options and creates a new 'Pizza' object
     * using the 'PizzaMaker' class
     *
     * @param holder holder
     * @return a new 'Pizza' object based on selected options
     */
    private Pizza pizzaParse(@NonNull ViewHolder holder) {
        String selectedPizza = holder.pizzaName.getText().toString();
        String extraSauceCheck = "false";
        String extraCheeseCheck = "false";
        int selectedRadioButtonId = holder.sizeGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = holder.itemView.findViewById(selectedRadioButtonId);
        String selectedSize = selectedRadioButton.getText().toString();
        if (holder.checkBoxExtraSauce.isChecked())
            extraSauceCheck = "true";
        if (holder.checkBoxExtraCheese.isChecked())
            extraCheeseCheck = "true";
        return PizzaMaker.createPizza(selectedPizza + " " + selectedSize + " " + extraSauceCheck + " " + extraCheeseCheck);
    }

    /**
     * This inner class holds references to views for each item in the RecyclerView and initializes
     * them in the constructor.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Pizza pizza;
        private final List<String> quantity = Arrays.asList("1", "2", "3", "4");
        CheckBox checkBoxExtraSauce;
        CheckBox checkBoxExtraCheese;
        Button addToOrderButton;
        TextView priceTextView;
        ImageView pizzaImageView;
        Spinner quantitySpinner;
        TextView pizzaName;
        TextView toppings;
        RadioGroup sizeGroup;
        // Add other views as needed

        /**
         * This constructor initializes various views for each item in the RecyclerView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxExtraSauce = itemView.findViewById(R.id.extraSauce);
            checkBoxExtraCheese = itemView.findViewById(R.id.extraCheese);
            addToOrderButton = itemView.findViewById(R.id.addToOrder);
            priceTextView = itemView.findViewById(R.id.price);
            pizzaImageView = itemView.findViewById(R.id.pizzaPicture);
            quantitySpinner = itemView.findViewById(R.id.quantitySpinner);
            pizzaName = itemView.findViewById(R.id.pizzaName);
            sizeGroup = itemView.findViewById(R.id.specialtyRadioButtonGroup);
            toppings = itemView.findViewById(R.id.toppings);
            ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(itemView.getContext(),
                    android.R.layout.simple_spinner_item, quantity);
            quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            quantitySpinner.setAdapter(quantityAdapter);
        }

        /**
         * This method loads and displays the pizza image based on the provided image name
         *
         * @param imageName
         */
        public void bind(String imageName) {
            int resourceId = itemView.getContext().getResources().getIdentifier(imageName, "drawable", itemView.getContext().getPackageName());

            if (resourceId != 0) {
                pizzaImageView.setImageResource(resourceId);
            }
        }

    }
}
