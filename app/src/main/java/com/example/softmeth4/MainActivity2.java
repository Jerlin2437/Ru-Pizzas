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
                    Intent intent = getIntent();
                    String fragmentTag = intent.getStringExtra("FRAGMENT_TAG");

                    if (fragmentTag != null) {
                        switch (fragmentTag) {
                            case "special_pizzas":
                                replaceFragment(specialPizzasFragment);
                                return true;
                            case "byo_pizzas":
                                replaceFragment(byoPizzasFragment);
                                return true;
                            case "current_order":
                                replaceFragment(currentOrderFragment);
                                return true;
                            case "store_orders":
                                replaceFragment(storeOrdersFragment);
                                return true;
                        }
                    }
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
