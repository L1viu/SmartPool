package com.example.finalyearproject;


import android.os.Bundle;
import android.content.Intent;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //login fields + buttons
    EditText etEmail, etPassword;
    Button btnLogin;

    //Firebase authenticator
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //display login page
        setContentView(R.layout.activity_login);

        // Get all the fields from the xml
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Connect to the firebase
        auth = FirebaseAuth.getInstance();

        //login button
        btnLogin.setOnClickListener(v -> loginUser());
    }

    private void loginUser() {

        //String to get email+password set by the user
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        // Email must exist and be valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email");
            return;
        }

        //if password is empty enter your password
        if (password.isEmpty()) {
            etPassword.setError("Enter your password");
            return;
        }

        //Disable button while logging in
        btnLogin.setEnabled(false);

        // Use firebase to check email and pasword
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {

            //enable button again
            btnLogin.setEnabled(true);

            if (task.isSuccessful()) {

                //login succesful
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                // Open the home page
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);

                //clear the previous screen
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();

            } else {

                //Login failed
                Toast.makeText(this, "Email or password is not valid", Toast.LENGTH_LONG).show();
            }
        });
    }
}
