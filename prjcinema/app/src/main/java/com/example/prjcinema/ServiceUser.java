package com.example.prjcinema;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ServiceUser {
    private DatabaseReference databaseReference;

    public ServiceUser() {
        // Initialize Firebase Database Reference
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("users"); // Node in Firebase
    }

    // Method to save user data
    public void saveUser(String name, String email, String password) {
        // Generate a unique key for each user
        String userId = databaseReference.push().getKey();

        if (userId != null) {
            User user = new User(name, email, password);
            databaseReference.child(userId).setValue(user)
                    .addOnSuccessListener(aVoid -> Log.d("ServiceUser", "User saved successfully."))
                    .addOnFailureListener(e -> Log.e("ServiceUser", "Error saving user: " + e.getMessage()));
        }
    }
}
