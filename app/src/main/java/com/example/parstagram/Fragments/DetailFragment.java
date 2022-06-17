package com.example.parstagram.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.example.parstagram.Post;
import com.example.parstagram.R;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

public class DetailFragment extends Fragment {
    FragmentActivity listener;
    TextView tvDetailUsername;
    TextView tvDetailTimestamp;
    TextView tvDetailDescription;
    ImageView ivDetailImage;
    private Post mPost;
    ImageView ivDetailLike;
    TextView tvDetailLikes;

    // This event fires 1st, before creation of fragment or any views
    // The onAttach method is called when the Fragment instance is associated with an Activity.
    // This does not mean the Activity is fully initialized.
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        mPost = Parcels.unwrap(getArguments().getParcelable("post"));
    }

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // onViewCreated() is only called if the view returned from onCreateView() is non-null.
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvDetailDescription = view.findViewById(R.id.tvDetailDescription);
        tvDetailUsername = view.findViewById(R.id.tvDetailUsername);
        tvDetailTimestamp = view.findViewById(R.id.tvDetailTimestamp);
        ivDetailImage = view.findViewById(R.id.ivDetailImage);
        ivDetailLike = view.findViewById(R.id.ivDetailLike);
        tvDetailLikes = view.findViewById(R.id.tvDetailLikes);

        //Values for views
        tvDetailDescription.setText(mPost.getDescription());
        tvDetailUsername.setText(mPost.getUser().getUsername());
        tvDetailTimestamp.setText(calculateTimeAgo(mPost.getCreatedAt()));
        tvDetailLikes.setText(mPost.getLikes() + " Likes");
        //check if post is liked and update image accordingly
        checkLikedPost();
        if (mPost.getImage() != null){
            ivDetailImage.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(mPost.getImage().getUrl()).override(1250).into(ivDetailImage);
        }
        else{
            ivDetailImage.setVisibility(View.INVISIBLE);
        }

        ivDetailLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (checkLikedPost()){
                        //remove like
                        removeLike();
                        Glide.with(getContext()).load(R.drawable.ufi_heart).into(ivDetailLike);
                    }
                    else{
                        //add like
                        addLike();
                        Glide.with(getContext()).load(R.drawable.ufi_heart_active).into(ivDetailLike);
                    }
                }
            }
        );



    }

    private void addLike() {
        //UPDATE like counter
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        // Retrieve the object by id
        query.getInBackground(mPost.getObjectId(), (object, e) -> {
            if (e == null) {
                // Update the number of likes
                mPost.setLikes(mPost.getLikes()+1);
                object.setLikes(mPost.getLikes());
                // Update the users
                ParseUser user = ParseUser.getCurrentUser();
                List <String> likedBy = mPost.getLikedBy();
                mPost.getLikedBy().add(0,user.getObjectId());
                object.setLikedBy(mPost.getLikedBy());
                // All other fields will remain the same
                object.saveInBackground();
                tvDetailLikes.setText(mPost.getLikes() + " Likes");


            } else {
                // something went wrong
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void removeLike() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);

        // Retrieve the object by id
        query.getInBackground(mPost.getObjectId(), (object, e) -> {
            if (e == null) {
                // Update the number of likes
                mPost.setLikes(mPost.getLikes()-1);
                object.setLikes(mPost.getLikes());
                // Update the users
                ParseUser user = ParseUser.getCurrentUser();
                List <String> likedBy = mPost.getLikedBy();
                mPost.getLikedBy().remove(user.getObjectId());
                object.setLikedBy(mPost.getLikedBy());
                // All other fields will remain the same
                object.saveInBackground();
                tvDetailLikes.setText(mPost.getLikes() + " Likes");

            } else {
                // something went wrong
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkLikedPost() {

        if (mPost.getLikedBy().contains(ParseUser.getCurrentUser().getObjectId())){
            Glide.with(getContext()).load(R.drawable.ufi_heart_active).into(ivDetailLike);
            return true;
        }
        else{
            Glide.with(getContext()).load(R.drawable.ufi_heart).into(ivDetailLike);
            return false;
        }

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

    //Helper function for timestamp
    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            Log.i("Error:", "getRelativeTimeAgo failed", e);
            e.printStackTrace();
        }

        return "";
    }

}
