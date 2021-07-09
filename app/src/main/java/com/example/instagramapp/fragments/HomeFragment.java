package com.example.instagramapp.fragments;

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

import com.example.instagramapp.Post;
import com.example.instagramapp.PostAdapter;
import com.example.instagramapp.R;
import com.example.instagramapp.databinding.FragmentHomeBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements PostAdapter.OnClickListener{

    private String TAG = "HomeFragment";
    RecyclerView rvPosts;
    List<Post> postsA;
    PostAdapter postAdapter;
    private SwipeRefreshLayout swipeContainer;

    public HomeFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(getLayoutInflater());

        // Defining the recycler view
        rvPosts = view.findViewById(R.id.rvPosts);

        // Defining the posts list
        postsA = new ArrayList<>();

        // Creating the adapter
        postAdapter = new PostAdapter( getContext(), postsA, this);

        // Defining the recycler view adapter and layout manager
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setAdapter(postAdapter);


        // Getting the swipe container view
        swipeContainer = view.findViewById(R.id.swipeCont);

        // Setting the listener for the swipe container
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryPosts();
                swipeContainer.setRefreshing(false);
            }
        });

        // query posts from Parstagram
        queryPosts();
    }

    //Getting all of the posts
    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                postAdapter.clear();
                for (Post post : posts){
                    Log.i(TAG, "Post: " + post.getDescription() + ", Username: " + post.getUser().getUsername());
                }
                postAdapter.addAll(posts);
            }
        });
    }

    @Override
    public void onLikeClick(int position) {

    }

    @Override
    public void onCommentsClick(int position) {
        Fragment fragment = new CommentsFragment(postsA.get(position));
        getChildFragmentManager().beginTransaction().replace(R.id.childLayout, fragment).commit();
    }

    @Override
    public void onCommentButtonClick(int position) {

    }
}

