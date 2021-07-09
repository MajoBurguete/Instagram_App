package com.example.instagramapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.instagramapp.Post;
import com.example.instagramapp.PostAdapter;
import com.example.instagramapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements PostAdapter.OnClickListener{

    private String TAG = "ProfileFragment";
    ImageView ivProfPict;
    TextView tvProfileName;
    TextView tvNameP;
    TextView tvProfileDesc;
    RecyclerView rvPosts;
    List<Post> postsUser;
    PostAdapter adapter;
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

        // Defining the adapter
        adapter = new PostAdapter(getContext(), postsUser, this);

        // Initialize post array
        postsUser = new ArrayList<>();

        // Refresh listener
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });

    }

    @Override
    public void onLikeClick(int position) {

    }

    @Override
    public void onCommentsClick(int position) {

    }

    @Override
    public void onCommentButtonClick(int position) {

    }
}