package com.example.finalyearproject;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //Buttons coming from the xml layout
    Button btnLogin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Display the welcome screen
        setContentView(R.layout.activity_main);

        //Get the buttons from the XML
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        //Login Button
        btnLogin.setOnClickListener(v -> {

            //Open login page
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

//Signup Button
        btnSignup.setOnClickListener(v -> {

            //Open signup page
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        });

    }
}
