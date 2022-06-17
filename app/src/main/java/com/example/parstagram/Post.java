package com.example.parstagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

//@Parcel
@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_IMAGE = "image";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_USER = "user";
    public static final String KEY_LIKES = "likes";
    public static final String KEY_LIKEDBY = "likedBy";


    public Post (){}


    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    public void setLikes(int likes){
        put(KEY_LIKES, likes);
    }

    public int getLikes() {
        return getInt(KEY_LIKES);
    }

    public void setLikedBy(List<String> users) {
        put(KEY_LIKEDBY, users);
    }

    public List<String> getLikedBy(){
       List<String> likedBy = getList(KEY_LIKEDBY);
       if(likedBy == null){
           likedBy = new ArrayList<>();
       }
       return likedBy;
    }
}
