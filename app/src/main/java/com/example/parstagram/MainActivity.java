package com.example.parstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button btLogout;
    ParseUser user;
    private ArrayList<Post> mPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Lookup the recyclerview in activity layout
        RecyclerView rvFeed = (RecyclerView) findViewById(R.id.rvFeed);

        // Create adapter passing in the sample user data
        PostAdapter adapter = new PostAdapter(this, mPosts);
        // Attach the adapter to the recyclerview to populate items
        rvFeed.setAdapter(adapter);
        // Set layout manager to position the items
        rvFeed.setLayoutManager(new LinearLayoutManager(this));
        // That's all!
        user = ParseUser.getCurrentUser();
        btLogout = findViewById(R.id.btLogout);
        queryPosts();

        //Logout Button onClick
        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });





    }

    public void queryPosts(){
        // Specify which class to query
        //Post post = new Post();
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        Log.i(TAG, query.toString());
        // Specify the object id
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, com.parse.ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    mPosts.addAll(objects);
                    Log.i(TAG, mPosts.toString());
                } else {
                    Log.e("item", "Error: " + e.getMessage());
                }
            }

        });



    }
}