package com.example.softmeth4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * This class serves as the main menu for the pizza app, with a BottomNavigationView
 * to navigate between different fragments that represent different ways to order pizzas
 * and ways to view pizza orders. This class sets up fragment instances and handles navigation
 * between selected menu items.
 *
 * @author Jerlin Yuen
 */

public class MainActivity2 extends AppCompatActivity {

    private final fragment_special_pizzas specialPizzasFragment = new fragment_special_pizzas();
    private final fragment_byo_pizzas byoPizzasFragment = new fragment_byo_pizzas();
    private final fragment_current_order currentOrderFragment = new fragment_current_order();
    private final fragment_store_orders storeOrdersFragment = new fragment_store_orders();


    /**
     * This method sets the content view to the layout activity_main2.xml and sets up
     * the BottomNavigationView menu with an item selection listener. It allows for navigation
     * between fragments through the calling of the replaceFragment method.
     *
     * @param savedInstanceState saved state data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.navigation_special_pizzas) {
                        replaceFragment(specialPizzasFragment);
                        return true;
                    } else if (item.getItemId() == R.id.navigation_byo_pizzas) {
                        replaceFragment(byoPizzasFragment);
                        return true;
                    } else if (item.getItemId() == R.id.navigation_current_order) {
                        replaceFragment(currentOrderFragment);
                        return true;
                    } else if (item.getItemId() == R.id.navigation_store_orders) {
                        replaceFragment(storeOrdersFragment);
                        return true;
                    } else
                        return false;
                }
        );

        replaceFragment(specialPizzasFragment);
    }

    /**
     * This method sets the content view to the layout activity_main2.xml and sets up
     * the BottomNavigationView menu with an item selection listener. It allows for navigation
     * between fragments through the calling of the replaceFragment method.
     *
     * @param fragment one of 4 fragments to be replaced/swapped (specialty pizza, byo pizza, current order, and store orders)
     */
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}

