package com.example.prjcinema;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPassword;
    private Button btnSignUp;

    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup); // Signup layout

        // Initialize Firebase reference
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");

        // Find views
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnCreateAccount);

        // Sign up button listener
        btnSignUp.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (validateInputs(name, email, password)) {
                addUserToFirebase(name, email, password);
            }
        });
    }

    private boolean validateInputs(String name, String email, String password) {
        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            return false;
        }
        return true;
    }

    private void addUserToFirebase(String name, String email, String password) {
        String userId = databaseUsers.push().getKey();
        if (userId != null) {
            User user = new User(name, email, password);
            databaseUsers.child(userId).setValue(user)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the signup activity and go back to login
                    })
                    .addOnFailureListener(e -> Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}
