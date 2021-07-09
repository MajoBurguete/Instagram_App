package com.example.instagramapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instagramapp.Post;
import com.example.instagramapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    ImageView ivProfPict;
    TextView tvProfileName;
    TextView tvNameP;
    TextView tvProfileDesc;
    RecyclerView rvPosts;
    List<Post> postsUser;
    private SwipeRefreshLayout swipeContainer;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivProfPict = view.findViewById(R.id.ivProfPict);
        tvNameP = view.findViewById(R.id.tvNameP);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvProfileDesc = view.findViewById(R.id.tvProfileDesc);
        swipeContainer = view.findViewById(R.id.scProfile);

        // Get the reference from the recycler view in the profile layout
        rvPosts = view.findViewById(R.id.rvUserPosts);

        // Initialize post array
        postsUser = new ArrayList<>();
    }
}