package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.instagramapp.databinding.ActivityLoginBinding;
import com.example.instagramapp.databinding.ActivityMainBinding;

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
        etUsername = binding.etUsername;
        etPassword = binding.etPassword;
        btnLogin = binding.btnLogin;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "It can't be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.i(TAG, "onClick login button");
                loginUser(username, password);
            }
        });
    }

    private void loginUser(String username, String password) {
        Log.i(TAG, " Attempting to login user " + username);
    }
}