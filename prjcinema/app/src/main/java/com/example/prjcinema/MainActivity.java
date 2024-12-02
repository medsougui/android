package com.example.prjcinema;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView errorMessage, textViewCreateAccount;
    private Button buttonLogin;

    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Login layout

        // Initialize Firebase reference
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        // Find views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        errorMessage = findViewById(R.id.errorMessage);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewCreateAccount = findViewById(R.id.textViewCreateAccount);

        // Login button listener
        buttonLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                validateLogin(email, password);
            } else {
                showError("Please enter both email and password");
            }
        });

        // "Create Account" link listener
        textViewCreateAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    // Validate login credentials from Firebase
    private void validateLogin(String email, String password) {
        databaseUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean isValid = false;

                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    String dbEmail = userSnapshot.child("email").getValue(String.class);
                    String dbPassword = userSnapshot.child("password").getValue(String.class);

                    if (dbEmail != null && dbEmail.equals(email) && dbPassword != null && dbPassword.equals(password)) {
                        isValid = true;
                        break;
                    }
                }

                if (isValid) {
                    navigateToHomePage();
                } else {
                    showError("Invalid email or password");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                showError("An error occurred. Please try again.");
            }
        });
    }

    // Navigate to Home Page
    private void navigateToHomePage() {
        Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }

    // Show error message
    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setVisibility(View.VISIBLE);
    }
}
