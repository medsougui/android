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

    public FilmService() {
        // Initialize Firebase Realtime Database reference
        database = FirebaseDatabase.getInstance().getReference("Films");
    }

    // Method to add a film
    public void addFilm(Film film) {
        database.child(film.getId()).setValue(film)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Film added successfully!"))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding film", e));
    }

    // Method to retrieve all films and log them
    public void getAllFilms() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Film> films = new ArrayList<>();
                for (DataSnapshot filmSnapshot : snapshot.getChildren()) {
                    Film film = filmSnapshot.getValue(Film.class);
                    if (film != null) {
                        films.add(film);
                        Log.d(TAG, "Retrieved film: " + film.getName() + " - " + film.getGenre());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Error retrieving films", error.toException());
            }
        });
    }
}
