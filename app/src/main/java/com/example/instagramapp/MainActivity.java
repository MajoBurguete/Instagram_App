package com.example.instagramapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.instagramapp.databinding.ActivityMainBinding;
import com.example.instagramapp.fragments.ComposeFragment;
import com.example.instagramapp.fragments.HomeFragment;
import com.example.instagramapp.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

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
                    Toast.makeText(MainActivity.this, "Post!", Toast.LENGTH_SHORT).show();
                    fragment = new ComposeFragment();
                }
                if (item.getItemId() == R.id.btnHome){
                    // TO DO: Update fragment
                    Toast.makeText(MainActivity.this, "Home!", Toast.LENGTH_SHORT).show();
                    fragment = new HomeFragment();
                }
                if (item.getItemId() == R.id.btnProfile){
                    Toast.makeText(MainActivity.this, "Profile!", Toast.LENGTH_SHORT).show();
                    fragment = new ProfileFragment();
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });

        binding.bottomNavigation.setSelectedItemId(R.id.btnHome);
    }
}