package com.example.eventify;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Bottom Navigation View
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);



        // Go to Payments Activity button functionality
        Button buttonToPay = findViewById(R.id.go_to_pay);
        if (buttonToPay != null) {
            buttonToPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, PaymentsActivity.class);
                    startActivity(intent);
                }
            });
        }

        // Go to Create Events Activity button functionality
        Button buttonToCreate = findViewById(R.id.go_to_create);
        if (buttonToCreate != null) {
            buttonToCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, CreateEventsAcitivity.class);
                    startActivity(intent);
                }
            });
        }

        // Bottom Navigation Item Selection
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = null;

                if (item.getItemId() == R.id.nav_home) {
                    recreate();
                    //intent = new Intent(HomeActivity.this, MainActivity.class);
                } else if (item.getItemId() == R.id.nav_events) {
                    intent = new Intent(HomeActivity.this, EventsActivity.class);
                } else if (item.getItemId() == R.id.nav_profile) {
                    intent = new Intent(HomeActivity.this, ProfileActivity.class);
                } else if (item.getItemId() == R.id.nav_notifications) { // Notifications
                    intent = new Intent(HomeActivity.this, NotificationsActivity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}


