package com.example.instagramapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instagramapp.PhotoActivity;
import com.example.instagramapp.R;
import com.example.instagramapp.databinding.ActivityPhotoBinding;
import com.parse.ParseUser;

public class ComposeFragment extends Fragment {

    private EditText etDescription;
    private Button btnTake;
    private Button btnSubmit;
    private ImageView ivPicture;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActivityPhotoBinding binding = ActivityPhotoBinding.inflate(getLayoutInflater());
        Toolbar toolbar = findViewById(R.id.photoToolbar);
        setSupportActionBar(toolbar);
        etDescription = binding.etDescription;
        btnTake = binding.btnTake;
        btnSubmit = binding.btnSubmit;
        ivPicture = binding.ivPicture;

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save post
                String description = binding.etDescription.getText().toString();
                if (description.isEmpty() || binding.ivPicture.getDrawable() == null){
                    Toast.makeText(getContext(), "Make sure to not leave anything blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(description, currentUser, photoFile, binding);
            }
        });

        binding.btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Launch camera
                launchCamera();
            }
        });

    }
}