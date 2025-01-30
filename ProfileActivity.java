package com.example.eventify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize views
        ImageView profileImage = findViewById(R.id.profile_image);
        TextView nameText = findViewById(R.id.name_text);
        TextView emailText = findViewById(R.id.email_text);
        Button editProfileButton = findViewById(R.id.edit_profile_button);
        Button logoutButton = findViewById(R.id.logout_button);

        // Get the current authenticated user
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            // Set user data
            String email = currentUser.getEmail();
            emailText.setText(email);

            // Reference to Realtime Database or Firestore
            String userId = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

            // Fetch user name from Realtime Database
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // Retrieve the name from the database
                        String name = snapshot.child("name").getValue(String.class);
                        if (name != null && !name.isEmpty()) {
                            nameText.setText(name);
                        } else {
                            nameText.setText("User"); // Fallback if name is missing
                        }
                    } else {
                        nameText.setText("User"); // Fallback if no user data exists
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProfileActivity.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Redirect to sign-in activity if no user is logged in
            Toast.makeText(this, "No user logged in. Redirecting to sign-in page.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        // Edit Profile Button Action
        editProfileButton.setOnClickListener(v ->
                Toast.makeText(ProfileActivity.this, "Edit Profile button clicked", Toast.LENGTH_SHORT).show());

        // Logout Button Action
        logoutButton.setOnClickListener(v -> {
            // Sign out from Firebase
            firebaseAuth.signOut();

            // Redirect to SignInActivity
            Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
            startActivity(intent);

            // Close the application
            finishAffinity(); // Closes all activities
        });
    }
}
