package com.example.instagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.instagramapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    RecyclerView rvPosts;
    List<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Setting the bottom navigation listener
        bottomItemSelected(binding);

        // Defining the posts list
        posts = new ArrayList<>();

        // Defining the recycler view
        rvPosts = binding.rvPosts;

        // Creating the adapter
        PostAdapter postAdapter = new PostAdapter( this, posts);

        // Defining the recycler view adapter and layout manager
        rvPosts.setLayoutManager(new LinearLayoutManager(this));
        rvPosts.setAdapter(postAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.photo){
            Intent takeP = new Intent(MainActivity.this, PhotoActivity.class);
            startActivity(takeP);
        }
        return true;
    }

    private void bottomItemSelected(ActivityMainBinding binding) {
        binding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.logout){
                    ParseUser.logOut();
                    ParseUser currentUser = ParseUser.getCurrentUser();
                }
                Intent j = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(j);
                finish();
                return true;
            }
        });
    }
}