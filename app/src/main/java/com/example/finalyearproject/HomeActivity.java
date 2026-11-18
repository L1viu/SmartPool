package com.example.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    TextView txtWelcome;
    Button btnLogout, btnCheckBackend;
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
        btnCheckBackend = findViewById(R.id.btnCheckBackend);

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

        //check the python backend connection
        btnCheckBackend.setOnClickListener(v -> checkBackendStatus());
    }


    private void checkBackendStatus() {

        // Button disabled while it is checking
        btnCheckBackend.setEnabled(false);

        // Connection running on a separate thread
        new Thread(() -> {

            HttpURLConnection connection = null;

            try {
                // Connect to the backend in python
                URL url = new URL("http://127.0.0.1:8000/health"); //local testing on my android phone at this stage
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                // Check what Python is responding with
                int responseCode = connection.getResponseCode();

                if (responseCode != 200) {
                    throw new IOException("HTTP error: " + responseCode);
                }

                // Read pythons response
                StringBuilder response = new StringBuilder();

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }

                // Get a status back from json
                JSONObject json = new JSONObject(response.toString());
                String status = json.optString("status", "unknown");

                // show the result
                runOnUiThread(() -> {
                    btnCheckBackend.setEnabled(true);
                    Toast.makeText(this, "Backend status: " + status,
                            Toast.LENGTH_LONG).show();
                });

            } catch (Exception e) {

                // Get an error message if couldn't connect
                runOnUiThread(() -> {
                    btnCheckBackend.setEnabled(true);
                    Toast.makeText(this, "Connection failed: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

        }).start();
    }


    private void goToWelcome() {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
//Return to the welcome page and clear previous screens
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }
}
