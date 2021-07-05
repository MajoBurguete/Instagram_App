package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagramapp.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Checking if a user has already logged in to open the app in the main activity
        // Instead of login activity
        if (ParseUser.getCurrentUser()!= null){
            goMainActivity();
        }
        etUsername = binding.etUsername;
        etPassword = binding.etPassword;
        btnLogin = binding.btnLogin;

        //Setting the on click listener for th login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Verification to see if the username or the password is empty
                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "It can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.i(TAG, "onClick login button");
                // Method to login
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, " Attempting to login user " + username);
        // Navigate to the main activity if the user has logged in properly
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Failed to login", e);
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}