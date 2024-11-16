package com.example.prjcinema;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Predefined test credentials
    private static final String TEST_USERNAME = "test";
    private static final String TEST_PASSWORD = "123";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView errorMessage;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login); // Your login layout

        // Find views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        errorMessage = findViewById(R.id.errorMessage);
        buttonLogin = findViewById(R.id.buttonLogin);

        // Handle window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button click listener to handle login
        buttonLogin.setOnClickListener(v -> {
            String username = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            // Validate credentials
            if (validateLogin(username, password)) {
                // If credentials are correct, set content view to homepage.xml
                setContentView(R.layout.homepage);
            } else {
                // Show error message if credentials are incorrect
                errorMessage.setVisibility(View.VISIBLE);
            }
        });
    }

    // Method to validate login credentials
    private boolean validateLogin(String username, String password) {
        // Compare with predefined test credentials
        return TEST_USERNAME.equals(username) && TEST_PASSWORD.equals(password);
    }
}
