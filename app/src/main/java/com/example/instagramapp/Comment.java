package com.example.instagramapp;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

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

    public ParseObject getPost(){
        return getParseObject(KEY_POST);
    }

    public void setPost(ParseObject post){
        put(KEY_POST, post);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }


}
