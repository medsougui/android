package com.example.prjcinema;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private FilmService filmService;
    private GridLayout movieGrid;
    private EditText searchEditText;
    private Spinner categorySpinner;

    private ArrayList<Film> allFilms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        filmService = new FilmService();
        movieGrid = findViewById(R.id.movie_grid);
        searchEditText = findViewById(R.id.search_edit_text);
        categorySpinner = findViewById(R.id.category_spinner);

        Button buttonSearch = findViewById(R.id.button_search);
        Button buttonHome = findViewById(R.id.button_home);
        Button buttonCinema = findViewById(R.id.button_cinema);

        // Set up category spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Set spinner listener
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                performSearch(searchEditText.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Set button listeners
        buttonSearch.setOnClickListener(v -> performSearch(searchEditText.getText().toString()));
        buttonHome.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, HomePageActivity.class);
            startActivity(intent);
        });
        buttonCinema.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, CinemaActivity.class);
            startActivity(intent);
        });

        // Fetch all films
        fetchMovies();

        // Real-time search
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                performSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void fetchMovies() {
        filmService.getCategorizedFilms((actionMovies, horrorMovies, dramaMovies, comedyMovies, arabicMovies, featuredFilm) -> {
            allFilms = new ArrayList<>();
            allFilms.addAll(actionMovies);
            allFilms.addAll(horrorMovies);
            allFilms.addAll(dramaMovies);
            allFilms.addAll(comedyMovies);
            allFilms.addAll(arabicMovies);

            updateMovieGrid(allFilms); // Initially show all movies
        });
    }

    private void performSearch(String query) {
        String selectedCategory = categorySpinner.getSelectedItem().toString();
        ArrayList<Film> filteredFilms = new ArrayList<>();

        for (Film film : allFilms) {
            boolean matchesQuery = TextUtils.isEmpty(query) || film.getName().toLowerCase().contains(query.toLowerCase());
            boolean matchesCategory = selectedCategory.equals("All") || film.getGenre().equalsIgnoreCase(selectedCategory);

            if (matchesQuery && matchesCategory) {
                filteredFilms.add(film);
            }
        }
        updateMovieGrid(filteredFilms);
    }

    private void updateMovieGrid(ArrayList<Film> films) {
        movieGrid.removeAllViews();

        for (Film film : films) {
            View movieCard = getLayoutInflater().inflate(R.layout.movie_card, movieGrid, false);
            TextView movieTitle = movieCard.findViewById(R.id.movie_title);
            ImageView movieImage = movieCard.findViewById(R.id.movie_image);

            movieTitle.setText(film.getName());
            Glide.with(this).load(film.getPhoto()).into(movieImage);

            movieGrid.addView(movieCard);
        }
    }
}
