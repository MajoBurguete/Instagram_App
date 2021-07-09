package com.example.instagramapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramapp.Comment;
import com.example.instagramapp.CommentAdapter;
import com.example.instagramapp.Post;
import com.example.instagramapp.R;
import com.example.instagramapp.databinding.FragmentCommentsBinding;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CommentsFragment extends Fragment {

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

    public CommentsFragment(Post post) {
        this.post = post;
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

        // Layout references
        ivUserPic = view.findViewById(R.id.ivUserPic);
        etComment = view.findViewById(R.id.etComment);
        ibSendCom = view.findViewById(R.id.ibSendCom);
        btnCloseCom = view.findViewById(R.id.btnCloseCom);
        ParseUser user = ParseUser.getCurrentUser();
        Glide.with(getContext()).load(user.getParseFile(KEY_PROFILE).getUrl()).circleCrop().into(ivUserPic);


        btnCloseCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(CommentsFragment.this).commit();
            }
        });

        ibSendCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save Comment
                String comment = etComment.getText().toString();
                if (comment.isEmpty()){
                    Toast.makeText(getContext(), "Make sure to not leave anything blank", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(comment, currentUser);
            }
        });

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

    private void savePost(String comment, ParseUser currentUser) {
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
