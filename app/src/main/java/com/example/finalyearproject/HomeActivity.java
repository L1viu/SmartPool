package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    TextView txtWelcome;
    Button btnLogout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Check if user is logged in
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        // If there is no user prevent log in
        if (user == null) {
            goToWelcome();
            return;
        }
// load home page
        setContentView(R.layout.activity_home);

        txtWelcome = findViewById(R.id.txtWelcome);
        btnLogout = findViewById(R.id.btnLogout);

        // Display UserName from Firebase
        String name = user.getDisplayName();

        if (name != null && !name.isEmpty()) {
            txtWelcome.setText("Hi, " + name + "!");
        } else {
            txtWelcome.setText("Welcome back!");
        }

        // Logout button
        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            goToWelcome();
        });
    }

    private void goToWelcome() {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//Return to the welcome page and clear previous screens
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }
}
