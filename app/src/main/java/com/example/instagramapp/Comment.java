package com.example.instagramapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_POST = "postId";
    public static final String KEY_USER = "userId";

    public String getComment(){
        return getString(KEY_COMMENT);
    }

    public void setComment( String comment){
        put(KEY_COMMENT, comment);
    }

    public String getPost(){
        return getString(KEY_POST);
    }

    public void setPost(String id){
        put(KEY_POST, id);
    }

    public String getUser(){
        return getString(KEY_USER);
    }

    public void setUser(String id){
        put(KEY_USER, id);
    }


}
