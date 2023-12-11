package com.example.softmeth4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

/**
 * This class is a fragment class that serves as a menu for displaying a list of specialty pizzas in the pizza app.
 * The class provides a way to view each specialty pizza, customize specialty pizzas, and add them to the current order.
 *
 * @author Jerlin Yuen, Jason Lei
 */

public class fragment_special_pizzas extends Fragment {

    private final List<String> pizzaList = Arrays.asList("Cheese", "Chinese", "Deluxe", "Korean", "Meatzza", "Pepperoni",
            "Proteen", "Seafood", "Spham", "Supreme");

    private final List<String> pizzaPictures = Arrays.asList("cheesepizza", "chinesepizza", "deluxepizza",
            "koreanpizza", "meatzza", "pepperonipizza", "proteenpizza",
            "seafoodpizza", "shpampizza", "supremepizza");

    private RecyclerView recyclerView;
    private SpecialPizzaAdapter adapter;

    /**
     * Default empty constructor
     */
    public void SpecialPizzasFragment() {
        // Required empty public constructor
    }

    /**
     * General required Android fragment onCreate method called upon creation
     *
     * @param savedInstanceState saved state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * General required Android fragment onCreate method called upon creation.
     * Creates and returns the fragment's view and initializes a 'RecyclerView' to display
     * the specialty pizzas and sets up an adapter (SpecialPizzaAdapter) to populate the data into the view
     *
     * @param inflater           fragment_special_pizzas
     * @param container          container
     * @param savedInstanceState saved state
     */
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



