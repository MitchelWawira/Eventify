package com.example.eventify;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateEventsAcitivity extends AppCompatActivity {

    private EditText eventName;
    private EditText eventDate;
    private EditText eventTheme;
    private EditText eventPrice;
    private Button postButton;
    private DatabaseReference databaseEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_events);

        // Initialize Firebase Database reference
        databaseEvents = FirebaseDatabase.getInstance().getReference("events");

        // Initialize EditText and Button
        eventName = findViewById(R.id.eventName);
        eventDate = findViewById(R.id.eventDate);
        eventTheme = findViewById(R.id.eventTheme);
        eventPrice = findViewById(R.id.eventPrice);
        postButton = findViewById(R.id.postButton);

        // Set OnClickListener for the post button
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postEventToFirebase();
            }
        });
    }

    private void postEventToFirebase() {
        // Get the text from EditText fields
        String name = eventName.getText().toString().trim();
        String date = eventDate.getText().toString().trim();
        String theme = eventTheme.getText().toString().trim();
        String price = eventPrice.getText().toString().trim();

        // Check if any field is empty
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter the event name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (date.isEmpty()) {
            Toast.makeText(this, "Please enter the event date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (theme.isEmpty()) {
            Toast.makeText(this, "Please enter the event theme", Toast.LENGTH_SHORT).show();
            return;
        }
        if (price.isEmpty()) {
            Toast.makeText(this, "Please enter the event price", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a unique ID for each event
        String eventId = databaseEvents.push().getKey();

        // Create an Event object
        Event event = new Event(eventId, name, date, theme, price);

        // Save the event to Firebase
        if (eventId != null) {
            databaseEvents.child(eventId).setValue(event).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Event posted successfully!", Toast.LENGTH_SHORT).show();
                    clearFields(); // Clear the input fields
                } else {
                    Toast.makeText(this, "Failed to post event. Try again.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // Optional method to clear the EditText fields
    private void clearFields() {
        eventName.setText("");
        eventDate.setText("");
        eventTheme.setText("");
        eventPrice.setText("");
    }
}
