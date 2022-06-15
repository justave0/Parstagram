package com.example.parstagram;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Lookup the recyclerview in activity layout

        final FragmentManager fragmentManager = getSupportFragmentManager();
        RecyclerView rvFeed = (RecyclerView) findViewById(R.id.rvFeed);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //fragment
        final Fragment feed_fragment = new FeedFragment();
        final Fragment create_fragment = new CreateFragment();

        // Create adapter passing in the sample user data
        adapter = new PostAdapter(this, mPosts);
        // Attach the adapter to the recyclerview to populate items
        rvFeed.setAdapter(adapter);
        // Set layout manager to position the items
        rvFeed.setLayoutManager(new LinearLayoutManager(this));
        // That's all!


        user = ParseUser.getCurrentUser();
        queryPosts();

        //Fragment Manager
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_feed:
                        fragment = feed_fragment;
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.flFeed, fragment);
                        ft.commit();
                    case R.id.action_create:
                        // do something here
                        fragment = create_fragment;
                        break;
                    default:
                        fragment = feed_fragment;
                }
                fragmentManager.beginTransaction().replace(R.id.rlContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_feed);

    }


    //Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_posts, menu);
        return true;
    }

    public void onPostAction(MenuItem mi) {
        Log.i(TAG, "Click post");

    }

    public void onLogoutAction(MenuItem mi){
        ParseUser.logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }





    public void queryPosts(){
        // Specify which class to query
        //Post post = new Post();
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
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
                adapter.notifyDataSetChanged();
            }

        });



    }
}