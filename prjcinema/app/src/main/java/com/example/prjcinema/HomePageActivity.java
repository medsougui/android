package com.example.prjcinema;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {
    private FilmService filmService;

    // UI elements
    private LinearLayout actionMoviesLayout, horrorMoviesLayout, dramaMoviesLayout, comedyMoviesLayout, arabicMoviesLayout;
    private TextView featuredMovieTitle;
    private ImageView featuredMovieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // Initialize FilmService
        filmService = new FilmService();

        // Find the UI elements
        actionMoviesLayout = findViewById(R.id.inner_linear_layout_action);
        horrorMoviesLayout = findViewById(R.id.inner_linear_layout_horror);
        dramaMoviesLayout = findViewById(R.id.inner_linear_layout_drama);
        comedyMoviesLayout = findViewById(R.id.inner_linear_layout_comedy);
        arabicMoviesLayout = findViewById(R.id.inner_linear_layout_arabic);

        featuredMovieTitle = findViewById(R.id.featured_movie_title);
        featuredMovieImage = findViewById(R.id.featured_movie_image);

        // Set up navigation buttons
        setupNavigation();

        // Fetch movies and update UI
        fetchMovies();
    }

    private void setupNavigation() {
        Button buttonSearch = findViewById(R.id.button_search);
        Button buttonHome = findViewById(R.id.button_home);
        Button buttonCinema = findViewById(R.id.button_cinema);

        buttonSearch.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        buttonCinema.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, CinemaActivity.class);
            startActivity(intent);
        });
    }

    private void fetchMovies() {
        filmService.getCategorizedFilms((actionMovies, horrorMovies, dramaMovies, comedyMovies, arabicMovies, featuredFilm) -> {
            if (featuredFilm != null) {
                featuredMovieTitle.setText(featuredFilm.getName());
                Glide.with(HomePageActivity.this)
                        .load(featuredFilm.getPhoto())
                        .into(featuredMovieImage);
            }

            // Load movies into respective layouts
            loadMovies(actionMovies, actionMoviesLayout);
            loadMovies(horrorMovies, horrorMoviesLayout);
            loadMovies(dramaMovies, dramaMoviesLayout);
            loadMovies(comedyMovies, comedyMoviesLayout);
            loadMovies(arabicMovies, arabicMoviesLayout);
        });
    }

    private void loadMovies(ArrayList<Film> movies, LinearLayout layout) {
        for (Film movie : movies) {
            View movieCard = getLayoutInflater().inflate(R.layout.movie_card, null);
            TextView movieTitle = movieCard.findViewById(R.id.movie_title);
            ImageView movieImage = movieCard.findViewById(R.id.movie_image);

            movieTitle.setText(movie.getName());
            Glide.with(this).load(movie.getPhoto()).into(movieImage);

            layout.addView(movieCard);
        }
    }
}
