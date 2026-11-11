package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword;
    Button btnSignup;

    FirebaseAuth auth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
//get the fields from xml and start firebase
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnSignup.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
//get + validate user input
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (name.isEmpty()) {
            etName.setError("Enter your name");
            return;
        }

        //email does not match
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Minimum 6 characters");
            return;
        }

        btnSignup.setEnabled(false);

        //create a new user with firebase authenticator
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {

            if (task.isSuccessful()) {

                //get the user and save their display name
                FirebaseUser user = auth.getCurrentUser();

                if (user != null) {

                    UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(name).build();

                    user.updateProfile(profile).addOnCompleteListener(profileTask -> {
                        if (!profileTask.isSuccessful()) {
                            Toast.makeText(this, "Could not update display name", Toast.LENGTH_SHORT).show();
                        }

                        saveUserToFirestore(user, name, email);
                    });
                } else {
                    btnSignup.setEnabled(true);
                    Toast.makeText(this, "Account created, but user unavailable", Toast.LENGTH_LONG).show();
                }

                //manage registration errors
            } else {
                btnSignup.setEnabled(true);

                String error = task.getException() != null ? task.getException().getMessage() : "Registration failed";

                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveUserToFirestore(FirebaseUser user, String name, String email) {

        //store user details in a hashmap

        Map<String, Object> userData = new HashMap<>();

        userData.put("name", name);
        userData.put("email", email);
        userData.put("uid", user.getUid());
        userData.put("createdAt", FieldValue.serverTimestamp());

        //save user detials to Firestore
        db.collection("users").document(user.getUid()).set(userData).addOnSuccessListener(aVoid -> {


            //if succesful return to login
            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();

            auth.signOut();

            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);
            finish();
        }).addOnFailureListener(e -> {

            btnSignup.setEnabled(true);

            //Error failed to save to Firestore
            Toast.makeText(this, "Account created, but profile save failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}
