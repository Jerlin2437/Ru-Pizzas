package com.example.softmeth4;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.businesslogic.PizzaMaker;
import com.example.softmeth4.pizzas.Pizza;

import java.security.AccessController;
import java.util.Arrays;
import java.util.List;

public class SpecialPizzaAdapter extends RecyclerView.Adapter<SpecialPizzaAdapter.ViewHolder> {
    private Pizza pizza;
    private Order order;
    private List<String> pizzaList;

    public SpecialPizzaAdapter(List<String> pizzaList) {
        this.pizzaList = pizzaList;
        this.pizza = null;
        this.order = Order.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_special_pizza, parent, false);
        return new ViewHolder(view);
    }

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

        pizza = pizzaParse(holder);
        updatePrice(holder);
    }

    private void showToast(@NonNull ViewHolder holder, String message) {
        Toast.makeText(holder.itemView.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    private void updatePrice(@NonNull ViewHolder holder){
        if (pizza != null) {
            pizza = pizzaParse(holder);
            //base price without toppings
            double basePrice = pizza.price();

            String formattedValue = String.format("%.2f", basePrice);
            holder.priceTextView.setText(formattedValue);
        }
    }
    private Pizza pizzaParse(@NonNull ViewHolder holder) {
        String selectedPizza = holder.pizzaName.getText().toString();
        String extraSauceCheck = "false";
        String extraCheeseCheck = "false";
        int selectedRadioButtonId = holder.sizeGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = holder.itemView.findViewById(selectedRadioButtonId);
        String selectedSize = selectedRadioButton.getText().toString();
        if(holder.checkBoxExtraSauce.isChecked())
            extraSauceCheck = "true";
        if (holder.checkBoxExtraCheese.isChecked())
            extraCheeseCheck = "true";
        return PizzaMaker.createPizza(selectedPizza + " " + selectedSize + " " + extraSauceCheck + " " + extraCheeseCheck);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Pizza pizza;
        private List<String> quantity = Arrays.asList("1", "2", "3", "4");
        CheckBox checkBoxExtraSauce;
        CheckBox checkBoxExtraCheese;
        Button addToOrderButton;
        TextView priceTextView;
        ImageView pizzaImageView;
        Spinner quantitySpinner;
        TextView pizzaName;
        RadioGroup sizeGroup;
        // Add other views as needed

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
            // Initialize other views
            ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(itemView.getContext(),
                    android.R.layout.simple_spinner_item, quantity);
            quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            quantitySpinner.setAdapter(quantityAdapter);


        }
    }
}
