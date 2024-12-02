package com.example.prjcinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class CinemaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cinema); // Cinema page layout

        // Set up navigation buttons
        Button buttonSearch = findViewById(R.id.button_search);
        Button buttonHome = findViewById(R.id.button_home);
        Button buttonCinema = findViewById(R.id.button_cinema);

        // Handle navigation to Search
        buttonSearch.setOnClickListener(v -> {
            Intent intent = new Intent(CinemaActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        // Handle navigation to Home
        buttonHome.setOnClickListener(v -> {
            Intent intent = new Intent(CinemaActivity.this, HomePageActivity.class);
            startActivity(intent);
        });

        // Handle navigation to Cinema (already on this page)
        buttonCinema.setOnClickListener(v -> {
            // Optional: Provide feedback (e.g., toast message)
        });
    }
}
