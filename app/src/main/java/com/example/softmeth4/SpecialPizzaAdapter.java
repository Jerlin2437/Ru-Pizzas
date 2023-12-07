package com.example.softmeth4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softmeth4.pizzas.Pizza;

import java.util.List;

public class SpecialPizzaAdapter extends RecyclerView.Adapter<SpecialPizzaAdapter.ViewHolder> {

    private List<Pizza> specialPizzaList;
    private Context context;

    public SpecialPizzaAdapter(List<Pizza> specialPizzaList, Context context) {
        this.specialPizzaList = specialPizzaList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_special_pizza, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pizza specialPizza = specialPizzaList.get(position);

        // Set data to views in the ViewHolder based on specialPizza

        // For example:
        // holder.checkBoxExtraSauce.setChecked(specialPizza.isExtraSauceSelected());
        // holder.checkBoxExtraCheese.setChecked(specialPizza.isExtraCheeseSelected());
        // holder.radioButtonSmall.setChecked(specialPizza.isSmallSizeSelected());
        // ... and so on

        // Set other data and event listeners as needed
    }

    @Override
    public int getItemCount() {
        return specialPizzaList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBoxExtraSauce;
        CheckBox checkBoxExtraCheese;
        RadioButton radioButtonSmall;
        // Add other views as needed

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBoxExtraSauce = itemView.findViewById(R.id.extraSauce);
            checkBoxExtraCheese = itemView.findViewById(R.id.extraCheese);
            radioButtonSmall = itemView.findViewById(R.id.specialtySmall);
            // Initialize other views
        }
    }
}
