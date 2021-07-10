package com.example.instagramapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.instagramapp.databinding.ActivityLoginBinding;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.parceler.Parcels;

public class LoginActivity extends AppCompatActivity {

    private final int REQUEST_CODE_SIGN = 7;
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

        //Setting the on click listener for th login button
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();

                // Verification to see if the username or the password is empty
                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "It can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                // Method to login
                loginUser(username, password);
            }
        });

        binding.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sign = new Intent(LoginActivity.this, SignupActivity.class);
                startActivityForResult(sign, REQUEST_CODE_SIGN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_SIGN && resultCode == RESULT_OK){
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            Toast.makeText(LoginActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();
            loginUser(username, password);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loginUser(String username, String password) {
        // Navigate to the main activity if the user has logged in properly
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null){
                    Toast.makeText(LoginActivity.this, "Failed to login, username or password incorrect", Toast.LENGTH_SHORT).show();
                    return;
                }
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Starts the intent to go to the main activity once the user is logged in
    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}