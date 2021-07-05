package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.instagramapp.databinding.ActivityPhotoBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class PhotoActivity extends AppCompatActivity {

    private String TAG = "PhotoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPhotoBinding binding = ActivityPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Toolbar toolbar = findViewById(R.id.photoToolbar);
        setSupportActionBar(toolbar);

        //queryPosts();

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save post
                String description = binding.etDescription.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(PhotoActivity.this, "Description can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser, binding);
            }
        });

        binding.btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch camera
            }
        });

    }

    private void savePost(String description, ParseUser currentUser, ActivityPhotoBinding binding) {
        Post post = new Post();
        post.setDescription(description);
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error while saving post", e);
                    Toast.makeText(PhotoActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(PhotoActivity.this, "Post was save successfully", Toast.LENGTH_SHORT).show();
                binding.etDescription.setText("");
                binding.ivPicture.setImageResource(0);
            }
        });
    }

    //Getting all of the posts
    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                for (Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", Username: " + post.getUser().getUsername());
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId() == R.id.closePhoto){
            finish();
        }
        return true;
    }
}