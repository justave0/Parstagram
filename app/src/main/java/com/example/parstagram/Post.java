package com.example.parstagram;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_IMAGE = "image";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_USER = "user";

    public Post (){
        super();
    }

    public Post (String description, ParseUser user, ParseFile image){
        super();
        setImage(image);
        setUser();
        setDescription(description);
    }

    public Post (String description, ParseUser user){
        super();
        setUser();
        setDescription(description);
    }



    public void setDescription(String description){
        put("description", description);
    }

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setUser(){
        put("user", ParseUser.getCurrentUser());
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }


    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile) {
        put("image", parseFile);
    }
}
