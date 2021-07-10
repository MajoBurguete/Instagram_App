package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class CommentsActivity extends AppCompatActivity {

    private String TAG = "CommentsFragment";
    public static final String KEY_PROFILE = "profilePic";
    RecyclerView rvComments;
    List<Comment> commentList;
    CommentAdapter commentAdapter;
    Post post;
    ImageView ivUserPic;
    EditText etComment;
    ImageButton ibSendCom;
    ImageButton btnCloseCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        // Post object
        post = Parcels.unwrap(getIntent().getParcelableExtra("post"));

        // Layout references
        ivUserPic = findViewById(R.id.ivUserPic);
        etComment = findViewById(R.id.etComment);
        ibSendCom = findViewById(R.id.ibSendCom);
        btnCloseCom = findViewById(R.id.btnCloseCom);
        ParseUser user = ParseUser.getCurrentUser();
        Glide.with(this).load(user.getParseFile(KEY_PROFILE).getUrl()).circleCrop().into(ivUserPic);


        btnCloseCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ibSendCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save Comment
                String comment = etComment.getText().toString();
                if (comment.isEmpty()){
                    Toast.makeText(CommentsActivity.this, "Make sure to not leave anything blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(comment, currentUser);
            }
        });

        //Defining the recycler view
        rvComments = findViewById(R.id.rvComments);

        // Defining the comment list
        commentList = new ArrayList<>();

        // Creating the adapter
        commentAdapter = new CommentAdapter(this, commentList);

        // Defining the recycler view adapter and layout manager
        rvComments.setAdapter(commentAdapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));

        queryComments();
    }

    private void savePost(String comment, ParseUser currentUser) {
        Comment commentObj= new Comment();
        commentObj.setComment(comment);
        commentObj.setUser(currentUser);
        commentObj.setPost(post);
        commentObj.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error while saving comment", e);
                    Toast.makeText(CommentsActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(CommentsActivity.this, "Comment was save successfully", Toast.LENGTH_SHORT).show();
                etComment.setText("");
                queryComments();
            }
        });

    }

    //Getting all of the posts
    private void queryComments() {
        ParseQuery<Comment> query = ParseQuery.getQuery(Comment.class);
        query.include(Comment.KEY_USER);
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
                List<Comment> commentsPost = new ArrayList<>();
                for (Comment comment: comments){
                    if (comment.getPost().getObjectId().equals(post.getObjectId())){
                        commentsPost.add(comment);
                    }
                }
                commentAdapter.addAll(commentsPost);
            }
        });
    }
}