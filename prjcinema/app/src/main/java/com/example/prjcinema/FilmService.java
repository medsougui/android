package com.example.prjcinema;

import android.util.Log;
import com.example.prjcinema.Film;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FilmService {
    private static final String TAG = "FilmService";
    private DatabaseReference database;

    public interface FilmDataCallback {
        void onDataRetrieved(ArrayList<Film> actionMovies, ArrayList<Film> horrorMovies,
                             ArrayList<Film> dramaMovies, ArrayList<Film> comedyMovies,
                             ArrayList<Film> arabicMovies, Film featuredFilm);
    }

    public FilmService() {
        database = FirebaseDatabase.getInstance().getReference("Films");
    }

    public void getCategorizedFilms(FilmDataCallback callback) {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Film> actionMovies = new ArrayList<>();
                ArrayList<Film> horrorMovies = new ArrayList<>();
                ArrayList<Film> dramaMovies = new ArrayList<>();
                ArrayList<Film> comedyMovies = new ArrayList<>();
                ArrayList<Film> arabicMovies = new ArrayList<>();

                Film featuredFilm = null;

                for (DataSnapshot filmSnapshot : snapshot.getChildren()) {
                    Film film = filmSnapshot.getValue(Film.class);
                    if (film != null) {
                        switch (film.getGenre()) {
                            case "Action":
                                actionMovies.add(film);
                                break;
                            case "Horror":
                                horrorMovies.add(film);
                                break;
                            case "Drama":
                                dramaMovies.add(film);
                                break;
                            case "Comedy":
                                comedyMovies.add(film);
                                break;
                            case "Arabic":
                                arabicMovies.add(film);
                                break;
                        }
                        if (featuredFilm == null || Integer.parseInt(film.getId()) > Integer.parseInt(featuredFilm.getId())) {
                            featuredFilm = film;
                        }
                    }
                }
                callback.onDataRetrieved(actionMovies, horrorMovies, dramaMovies, comedyMovies, arabicMovies, featuredFilm);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Error retrieving films", error.toException());
            }
        });
    }

    // New method to retrieve all films
    public void getAllFilms() {
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Film> allFilms = new ArrayList<>();

                for (DataSnapshot filmSnapshot : snapshot.getChildren()) {
                    Film film = filmSnapshot.getValue(Film.class);
                    if (film != null) {
                        allFilms.add(film); // Add all films to the list
                    }
                }
                // Log the list or use it as needed
                Log.d(TAG, "All films: " + allFilms);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Error retrieving all films", error.toException());
            }
        });
    }
}
