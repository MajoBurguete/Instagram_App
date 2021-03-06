package com.example.instagramapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.instagramapp.CommentsActivity;
import com.example.instagramapp.Post;
import com.example.instagramapp.PostAdapter;
import com.example.instagramapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment implements PostAdapter.OnClickListener{

    private String TAG = "ProfileFragment";
    public static final String KEY_PROFILE = "profilePic";
    public static final String KEY_NAME = "name";
    public static final String KEY_PROFILEDESC = "profileDescription";
    ImageView ivProfPict;
    TextView tvProfileName;
    TextView tvNameP;
    TextView tvProfileDesc;
    TextView tvNumber;
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
        tvNumber = view.findViewById(R.id.tvNumber);
        swipeContainer = view.findViewById(R.id.scProfile);

        // Set the user's data
        ParseUser user = ParseUser.getCurrentUser();
        Glide.with(getContext()).load(user.getParseFile(KEY_PROFILE).getUrl()).circleCrop().into(ivProfPict);
        tvNameP.setText(user.getString(KEY_NAME));
        tvProfileName.setText(user.getUsername());
        tvProfileDesc.setText(user.getString(KEY_PROFILEDESC));
        // Initialize post array
        postsUser = new ArrayList<>();

        // Get the reference from the recycler view in the profile layout
        rvPosts = view.findViewById(R.id.rvUserPosts);

        // Defining the adapter
        adapter = new PostAdapter(getContext(), postsUser, this);

        // Assign the adapter and the layout manager to the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setAdapter(adapter);

        // Refresh listener
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
                swipeContainer.setRefreshing(false);
            }
        });

        queryPosts();
    }

    //Getting all of the posts
    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                adapter.clear();
                for (Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", Username: " + post.getUser().getUsername());
                }
                adapter.addAll(posts);
                // Seting the number of posts after receiving them
                int value  = adapter.getItemCount();
                tvNumber.setText(String.valueOf(value));
            }
        });
    }

    @Override
    public void onLikeClick(int position) {

    }

    @Override
    public void onCommentsClick(int position) {
        Intent intent = new Intent(getContext(), CommentsActivity.class);
        intent.putExtra("post", Parcels.wrap(postsUser.get(position)));
        startActivity(intent);
    }

    @Override
    public void onCommentButtonClick(int position) {
        onCommentsClick(position);
    }
}