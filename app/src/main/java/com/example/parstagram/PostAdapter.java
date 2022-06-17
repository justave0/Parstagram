package com.example.parstagram;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.Fragments.DetailFragment;
import com.parse.ParseUser;
import com.parse.ui.widget.ParseImageView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private final Context context;
    private List<Post> mPosts;
    private String TAG = "PostAdapter";
    public DetailFragment detailFragment;



    public PostAdapter (Context context, List<Post> posts){
        this.mPosts = posts;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_post, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }




    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.bind(post);

    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvDescription;
        public TextView tvUsername;
        public ImageView ivPostImage;
        public ConstraintLayout clPost;
        public ImageView ivProfilePic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);
            clPost = itemView.findViewById(R.id.clPost);
            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);

        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            tvUsername.setText(post.getUser().getUsername());
            Glide.with(context).load(post.getUser().getParseFile("profilePic").getUrl()).circleCrop().into(ivProfilePic);
            //Parse Image View Methods
            if (post.getImage() != null) {
                ivPostImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(post.getImage().getUrl()).into(ivPostImage);
                ivPostImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Activity for adapter
                        AppCompatActivity activity = (AppCompatActivity) itemView.getContext();
                        //Fragment Transaction (idk)
                        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
                        //Create a new fragment
                        DetailFragment detailFragment = new DetailFragment();
                        //Create a new bundle
                        Bundle args = new Bundle();
                        //Put arguments in the bundle
                        args.putParcelable("post", Parcels.wrap(post));
                        //send bundle to the fragment
                        detailFragment.setArguments(args);
                        //replace the fragment
                        ft.replace(R.id.flMain, detailFragment);
                        //add to backstack
                        ft.addToBackStack("FeedFragment");
                        //Commit!
                        ft.commit();
                    }
                });
            }
            else{
                ivPostImage.setVisibility(View.INVISIBLE);
            }

        }
    }


    public void updateAdapter(ArrayList<Post> mDataList) {
        this.mPosts = mDataList;
        notifyDataSetChanged();
    }

    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mPosts.addAll(list);
        notifyDataSetChanged();
    }
}
