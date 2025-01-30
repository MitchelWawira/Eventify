package com.example.eventify;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        nameEditText = findViewById(R.id.name_edit);
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        Button signUpButton = findViewById(R.id.signup);
        TextView loginLinkButton = findViewById(R.id.login_link);

        // Set up the Sign-Up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(SignupActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignupActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password) || password.length() < 6) {
                    Toast.makeText(SignupActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a user with Firebase Authentication
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Get the registered user's UID
                                String userId = firebaseAuth.getCurrentUser().getUid();

                                // Save user data in Firebase Realtime Database
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

                                // Create a user object with name and email
                                HashMap<String, String> userData = new HashMap<>();
                                userData.put("name", name); // Name entered by the user
                                userData.put("email", email); // Email entered by the user

                                databaseReference.setValue(userData).addOnCompleteListener(saveTask -> {
                                    if (saveTask.isSuccessful()) {
                                        // User data saved successfully
                                        Toast.makeText(SignupActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                                        // Navigate to SignInActivity
                                        Intent intent = new Intent(SignupActivity.this, SignInActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Failed to save user data
                                        Toast.makeText(SignupActivity.this, "Failed to save user data: " + saveTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                // Registration failed
                                Toast.makeText(SignupActivity.this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        // Set up the Login link
        loginLinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignInActivity when "Login" link is clicked
                Intent intent = new Intent(SignupActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
