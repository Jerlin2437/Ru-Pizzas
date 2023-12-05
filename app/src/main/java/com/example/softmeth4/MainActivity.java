package com.example.softmeth4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assuming you have buttons with these IDs in your activity_main.xml layout
        View specialPizzasButton = findViewById(R.id.buttonSpecial);
        View byoPizzasButton = findViewById(R.id.buttonBYO);
        View currentOrderButton = findViewById(R.id.buttonCurrentOrder);
        View storeOrdersButton = findViewById(R.id.buttonStoreOrders);

        specialPizzasButton.setOnClickListener(v -> openMainActivity2("special_pizzas"));
        byoPizzasButton.setOnClickListener(v -> openMainActivity2("byo_pizzas"));
        currentOrderButton.setOnClickListener(v -> openMainActivity2("current_order"));
        storeOrdersButton.setOnClickListener(v -> openMainActivity2("store_orders"));
        
    }

    private void openMainActivity2(String fragmentTag) {
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("FRAGMENT_TAG", fragmentTag);
        startActivity(intent);
    }
}
