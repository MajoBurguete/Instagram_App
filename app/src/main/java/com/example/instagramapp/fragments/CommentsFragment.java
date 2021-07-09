package com.example.instagramapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramapp.Comment;
import com.example.instagramapp.CommentAdapter;
import com.example.instagramapp.Post;
import com.example.instagramapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends Fragment {

    private String TAG = "CommentsFragment";
    RecyclerView rvComments;
    List<Comment> commentList;
    CommentAdapter commentAdapter;

    public CommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Defining the recycler view
        rvComments = view.findViewById(R.id.rvComments);

        // Defining the comment list
        commentList = new ArrayList<>();

        // Creating the adapter
        commentAdapter = new CommentAdapter(getContext(), commentList);

        // Defining the recycler view adapter and layout manager
        rvComments.setAdapter(commentAdapter);
        rvComments.setLayoutManager(new LinearLayoutManager(getContext()));

        queryComments();
    }

    //Getting all of the posts
    private void queryComments() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_USER);
        query.include(Comment.KEY_POST);
        query.setLimit(20);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<Comment>() {
            @Override
            public void done(List<Comment> comments, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }
                commentAdapter.clear();
                for (Comment comment: comments){
                    Log.i(TAG, "Comment: " + comment.getComment() + ", Username: " + comment.getUser().getUsername() + ", post: "+comment.getPost().getString("objectId"));
                }
                commentAdapter.addAll(comments);
            }
        });
    }
}
