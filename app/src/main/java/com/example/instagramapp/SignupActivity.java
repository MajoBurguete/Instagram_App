package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.instagramapp.databinding.ActivitySignupBinding;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import org.parceler.Parcels;

public class SignupActivity extends AppCompatActivity {

    private String TAG = "SignupActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignupBinding binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = binding.ToolbarSign;
        setSupportActionBar(toolbar);

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values from the text view
                String username = binding.etUserSign.getText().toString();
                String password = binding.etPassSign.getText().toString();
                String email = binding.etEmail.getText().toString();

                // Check if everything is complete
                if (username.isEmpty() || password.isEmpty() || email.isEmpty()){
                    Toast.makeText(SignupActivity.this, "There's data missing, please fill everything!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create the ParseUser
                ParseUser user = new ParseUser();

                // Set properties
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);

                //Invoke signUpInBackground
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null){
                            Toast.makeText(SignupActivity.this, "Failed to signup, try again!", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Failed to signup", e);
                            return;
                        }
                        Intent result = new Intent();
                        result.putExtra("username", username);
                        result.putExtra("password", password);
                        setResult(RESULT_OK,result);
                        finish();
                    }
                });
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.btnReturnL){
            finish();
        }
        return true;
    }
}