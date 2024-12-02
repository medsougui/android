package com.example.prjcinema;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private FilmService filmService;
    private GridLayout movieGrid;
    private EditText searchEditText;
    private ArrayList<Film> allFilms; // To store all films

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search); // Your Search page layout

        // Initialize the FilmService and UI elements
        filmService = new FilmService();
        movieGrid = findViewById(R.id.movie_grid);
        searchEditText = findViewById(R.id.search_edit_text);

        Button buttonSearch = findViewById(R.id.button_search);
        Button buttonHome = findViewById(R.id.button_home);
        Button buttonCinema = findViewById(R.id.button_cinema);

        // Retrieve all films when the activity is created
        retrieveAllFilms();

        // Handle search button click
        buttonSearch.setOnClickListener(v -> performSearch(searchEditText.getText().toString()));

        // Handle navigation to Home
        buttonHome.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, HomePageActivity.class);
            startActivity(intent);
        });

        // Handle navigation to Cinema
        buttonCinema.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, CinemaActivity.class);
            startActivity(intent);
        });

        // Add a TextWatcher to search as the user types
        searchEditText.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                performSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable editable) {}
        });
    }

    // Method to retrieve all films from FilmService
    private void retrieveAllFilms() {
        filmService.getAllFilms();
        // Assuming the films are retrieved asynchronously. Use a callback to set the allFilms list after retrieval.
    }

    // Method to filter films based on search query
    private void performSearch(String query) {
        if (TextUtils.isEmpty(query)) {
            // If search query is empty, show all films
            updateMovieGrid(allFilms);
        } else {
            ArrayList<Film> filteredFilms = new ArrayList<>();
            for (Film film : allFilms) {
                if (film.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredFilms.add(film);
                }
            }
            updateMovieGrid(filteredFilms); // Update the grid with filtered films
        }
    }

    // Method to update the movie grid with the provided list of films
    private void updateMovieGrid(ArrayList<Film> films) {
        // Clear the existing grid
        movieGrid.removeAllViews();

        // Add the films to the grid
        for (Film film : films) {
            View movieCard = getLayoutInflater().inflate(R.layout.movie_card_small, movieGrid, false);
            ImageView movieImage = movieCard.findViewById(R.id.movie_image);
            TextView movieTitle = movieCard.findViewById(R.id.movie_title);

            // Set movie data (You can use an image loader like Picasso or Glide to load images)
            movieTitle.setText(film.getName());
            // Picasso.get().load(film.getPhoto()).into(movieImage); // Uncomment if using Picasso/Glide for images

            movieGrid.addView(movieCard);
        }
    }
}
