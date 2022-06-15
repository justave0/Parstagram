package com.example.parstagram.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parstagram.Post;
import com.example.parstagram.PostAdapter;
import com.example.parstagram.R;
import com.parse.FindCallback;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeedFragment extends Fragment {
    PostAdapter adapter;
    FragmentActivity listener;
    Context context;
    private ArrayList<Post> posts = new ArrayList<>();

    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof Activity){
            this.listener = (FragmentActivity) context;
        }
    }

    // This event fires 2nd, before views are created for the fragment
    // The onCreate method is called when the Fragment instance is being created, or re-created.
    // Use onCreate for any standard setup that does not require the activity to be fully created
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostAdapter(getActivity(), posts);
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rvFeed = (RecyclerView) view.findViewById(R.id.rvFeed);
        rvFeed.setAdapter(adapter);
        rvFeed.setLayoutManager(new LinearLayoutManager(context));
        queryPosts();
    }

    // This method is called when the fragment is no longer connected to the Activity
    // Any references saved in onAttach should be nulled out here to prevent memory leaks.
    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    // This method is called after the parent Activity's onCreate() method has completed.
    // Accessing the view hierarchy of the parent activity must be done in the onActivityCreated.
    // At this point, it is safe to search for activity View objects by their ID, for example.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    //Updates the adapter
    public void notifyAdapter (ArrayList<Post> post){
        posts = post;
        adapter.updateAdapter(posts);
    }

    public void queryPosts(){
        // Specify which class to query
        //Post post = new Post();
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        query.addDescendingOrder("createdAt");
        //Log.i(TAG, query.toString());
        // Specify the object id
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, com.parse.ParseException e) {
                if (e == null) {
                    // Access the array of results here
                    posts.addAll(objects);
                    notifyAdapter(posts);
                    //Log.i(TAG, mPosts.toString());
                } else {
                    Log.e("item", "Error: " + e.getMessage());
                }
            }

        });



    }
}
