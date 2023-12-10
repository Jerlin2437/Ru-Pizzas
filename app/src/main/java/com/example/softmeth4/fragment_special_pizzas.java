package com.example.softmeth4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softmeth4.businesslogic.Order;
import com.example.softmeth4.pizzas.Pizza;

import java.util.Arrays;
import java.util.List;

public class fragment_special_pizzas extends Fragment {

    private List<String> pizzaList = Arrays.asList("Cheese", "Chinese", "Deluxe", "Korean", "Meatzza", "Pepperoni",
            "Proteen", "Seafood", "Spham", "Supreme");

    private List<String> pizzaPictures = Arrays.asList("cheesepizza", "chinesepizza", "deluxepizza",
     "koreanpizza", "meatzza", "pepperonipizza", "proteenpizza",
    "seafoodpizza", "shpampizza", "supremepizza");

    private RecyclerView recyclerView;
    private SpecialPizzaAdapter adapter;

    public void SpecialPizzasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_special_pizzas, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSpecialPizzas);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SpecialPizzaAdapter(pizzaList, pizzaPictures);
        recyclerView.setAdapter(adapter);


        return view;
    }
}



