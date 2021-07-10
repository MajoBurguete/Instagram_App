package com.example.instagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.instagramapp.databinding.ActivityMainBinding;
import com.example.instagramapp.fragments.ComposeFragment;
import com.example.instagramapp.fragments.HomeFragment;
import com.example.instagramapp.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    final FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Setting the bottom navigation listener
        bottomItemSelected(binding);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.btnEditP){
            Intent i = new Intent(this, EditActivity.class);
            startActivity(i);
        }
        return true;
    }

    private void bottomItemSelected(ActivityMainBinding binding) {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = new HomeFragment();
                if (item.getItemId() == R.id.logout){
                    ParseUser.logOut();
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    Intent login = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(login);
                    finish();
                }
                if (item.getItemId() == R.id.btnPost){
                    fragment = new ComposeFragment();
                }
                if (item.getItemId() == R.id.btnHome){
                    fragment = new HomeFragment();
                }
                if (item.getItemId() == R.id.btnProfile){
                    fragment = new ProfileFragment();
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        binding.bottomNavigation.setSelectedItemId(R.id.btnHome);
    }
}