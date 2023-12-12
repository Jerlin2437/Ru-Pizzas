package com.example.softmeth4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This controller class is the main activity/base class that displays the screen before the main menu
 * for the pizza app (a welcome screen), while allowing for access to other fragments/displays for the rest of
 * the app.
 *
 * @author Jerlin Yuen
 */

public class MainActivity extends AppCompatActivity {


    /**
     * This method sets the content view to the layout activity_main.xml and finds a button with the ID
     * buttonStoreOrders, setting a click listener to it so that it begins a new activity labeled
     * openMainActivity2.
     *
     * @param savedInstanceState saved state data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View storeOrdersButton = findViewById(R.id.buttonStoreOrders);
        storeOrdersButton.setOnClickListener(v -> openMainActivity2("store_orders"));

    }

    /**
     * Updates the displayed pizza price based on selected toppings, size, and other options.
     *
     * @param fragmentTag fragment key
     */
    private void openMainActivity2(String fragmentTag) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("FRAGMENT_TAG", fragmentTag);
        startActivity(intent);
    }
}
