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

import com.example.parstagram.Fragments.CreateFragment;
import com.example.parstagram.Fragments.FeedFragment;
import com.example.parstagram.Fragments.ProfileFragment;
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

    Fragment feed_fragment;
    Fragment create_fragment;
    Fragment profile_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //fragment
        feed_fragment = new FeedFragment();
        create_fragment = new CreateFragment();
        profile_fragment = new ProfileFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flMain, feed_fragment, FeedFragment.class.getSimpleName());
        ft.commit();

        user = ParseUser.getCurrentUser();

        //Fragment Manager
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                String tag;
                switch (item.getItemId()) {
                    case R.id.action_create:
                        fragment = create_fragment;
                        tag = CreateFragment.class.getSimpleName();
                        break;

                    case R.id.action_feed:
                        fragment = feed_fragment;
                        tag = FeedFragment.class.getSimpleName();
                        break;

                    case R.id.action_profile:
                        fragment = profile_fragment;
                        tag = ProfileFragment.class.getSimpleName();
                        break;

                    default:
                        fragment = new Fragment();
                        tag = "rubbish";
                }

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flMain, fragment, tag);
                ft.commit();

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


    public void onLogoutAction(MenuItem mi){
        ParseUser.logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }






}