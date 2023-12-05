package com.example.softmeth4;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    private fragment_special_pizzas specialPizzasFragment = new fragment_special_pizzas();
    private fragment_byo_pizzas byoPizzasFragment = new fragment_byo_pizzas();
    private fragment_current_order currentOrderFragment = new fragment_current_order();
    private fragment_store_orders storeOrdersFragment = new fragment_store_orders();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.navigation_special_pizzas){
                        replaceFragment(specialPizzasFragment);
                        return true;
                    }
                    else if(item.getItemId() == R.id.navigation_byo_pizzas){
                        replaceFragment(byoPizzasFragment);
                        return true;
                    }
                    else if (item.getItemId() == R.id.navigation_current_order){
                        replaceFragment(currentOrderFragment);
                        return true;
                    }
                    else if (item.getItemId() == R.id.navigation_store_orders){
                        replaceFragment(storeOrdersFragment);
                        return true;
                    }
                    else
                        return false;
                }
        );

        // Set the initial fragment
        replaceFragment(specialPizzasFragment);
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}

