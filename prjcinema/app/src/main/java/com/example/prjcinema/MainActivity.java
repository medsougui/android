package com.example.prjcinema;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // Test credentials
    private static final String TEST_USERNAME = "testuser";
    private static final String TEST_PASSWORD = "12345";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView errorMessage;
    private Button buttonLogin;

    private DatabaseReference databaseFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        databaseFilms = FirebaseDatabase.getInstance().getReference("Films");

        setContentView(R.layout.login); // Login layout

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

            // Validate credentials with predefined test values
            validateLogin(username, password);
        });
    }

    // Method to validate login credentials
    private void validateLogin(String username, String password) {
        if (TEST_USERNAME.equals(username) && TEST_PASSWORD.equals(password)) {
            // Successful login
            Log.d(TAG, "Login successful");
            setContentView(R.layout.homepage);
            // Call methods to manage films after successful login
            manageFilms();
        } else {
            // Invalid credentials
            showError("Invalid username or password");
        }
    }

    // Helper method to show error messages
    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setVisibility(View.VISIBLE);
    }

    // Method to manage films (adding and retrieving films)
    private void manageFilms() {
        // Add sample films to the database
        addSampleFilms();

        // Retrieve and display all films in the console
        getAllFilms();
    }

    // Method to add sample films
    private void addSampleFilms() {
        Film film1 = new Film("1", "The Conjuring", "Horror", "https://m.media-amazon.com/images/I/71sH2xoOQwL._AC_SY679_.jpg", "2013-07-19");
        Film film2 = new Film("2", "Insidious", "Horror", "https://m.media-amazon.com/images/I/81LQm4Y7boL._AC_SY679_.jpg", "2010-09-14");
        Film film3 = new Film("3", "It", "Horror", "https://m.media-amazon.com/images/I/81p+xe8cbnL._AC_SY679_.jpg", "2017-09-08");

        databaseFilms.child(film1.getId()).setValue(film1);
        databaseFilms.child(film2.getId()).setValue(film2);
        databaseFilms.child(film3.getId()).setValue(film3);

        Log.d(TAG, "Sample films added to the database.");
    }

    // Method to retrieve and display all films
    private void getAllFilms() {
        databaseFilms.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot filmSnapshot : snapshot.getChildren()) {
                    Film film = filmSnapshot.getValue(Film.class);
                    if (film != null) {
                        Log.d(TAG, "Retrieved film: " + film.getName() + " - " + film.getGenre());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, "Error retrieving films: " + error.getMessage());
            }
        });
    }
}
