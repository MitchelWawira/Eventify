package com.example.eventify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {
    // Declare EditText for email, password, and FirebaseAuth instance
    private EditText emailEditText, passwordEditText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        Button signInButton = findViewById(R.id.sign_in_button);
        TextView signUpText = findViewById(R.id.sign_up_text);

        // Set an OnClickListener for the Sign In button
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Simple validation: Check if email and password are entered
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Sign in the user with Firebase Authentication
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Login successful
                                Toast.makeText(SignInActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish(); // Close the SignInActivity to prevent going back
                            } else {
                                // Login failed
                                Toast.makeText(SignInActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        // Set an OnClickListener for the Sign Up link
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the SignUpActivity
                Intent intent = new Intent(SignInActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
