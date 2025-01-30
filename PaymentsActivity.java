package com.example.eventify;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentsActivity extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText paymentEditText;
    private Button rsvpButton;
    private Button paymentButton;

    // Firebase reference to store payment details
    private DatabaseReference databasePayments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments); // Ensure your layout file is named activity_payment.xml

        // Initialize Firebase database reference
        databasePayments = FirebaseDatabase.getInstance().getReference("payments");

        // Initialize views
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        paymentEditText = findViewById(R.id.paymentEditText);
        rsvpButton = findViewById(R.id.rsvpButton);
        paymentButton = findViewById(R.id.paymentButton);

        // Initially disable the payment button
        paymentButton.setEnabled(false);

        // Set up a TextWatcher to monitor input changes
        TextWatcher inputWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInput(); // Check if the input fields are valid
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // Attach the TextWatcher to the EditText fields
        nameEditText.addTextChangedListener(inputWatcher);
        emailEditText.addTextChangedListener(inputWatcher);
        paymentEditText.addTextChangedListener(inputWatcher);

        // Set up the RSVP button click listener
        rsvpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle RSVP action
                Toast.makeText(PaymentsActivity.this, "RSVP Successful!", Toast.LENGTH_SHORT).show();
                savePaymentDetails("RSVP");
            }
        });

        // Set up the payment button click listener
        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle payment action
                Toast.makeText(PaymentsActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
                savePaymentDetails("Make Payment");
                clearForm();
            }
        });
    }

    // Method to validate input and enable/disable payment button
    private void validateInput() {
        // Check if all fields are filled
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String payment = paymentEditText.getText().toString().trim();

        // Enable the payment button if all fields are filled
        paymentButton.setEnabled(!name.isEmpty() && !email.isEmpty() && !payment.isEmpty());
    }

    // Method to save payment details to Firebase
    private void savePaymentDetails(String actionType) {
        // Get data from EditText fields
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String amountStr = paymentEditText.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || amountStr.isEmpty()) {
            // Show a message if any field is empty
            Toast.makeText(PaymentsActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);

        // Create a unique ID for the payment
        String paymentId = databasePayments.push().getKey();

        // Create a Payment object
        Payment payment = new Payment(name, email, amount, actionType);

        // Save the payment details to Firebase
        if (paymentId != null) {
            databasePayments.child(paymentId).setValue(payment)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(PaymentsActivity.this, "Payment details saved successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PaymentsActivity.this, "Failed to save payment details!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Method to clear form fields after payment is processed
    private void clearForm() {
        nameEditText.setText("");
        emailEditText.setText("");
        paymentEditText.setText("");
    }
}
