package com.example.parstagram;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;
import com.parse.ui.widget.ParseImageView;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    Context context;
    private List<Post> mPosts;
    private String TAG = "PostAdapter";


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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);

        }

        public void bind(Post post) {
            tvDescription.setText(post.getDescription());
            //ParseUser user = post.getUser();
            //String name = post.getUser().getUsername();
            //Log.i(TAG,name );
            tvUsername.setText(post.getUser().getUsername());


            //Parse Image View Methods
            if (post.getImage() != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(ivPostImage);
            }
        }

    }
    public void updateAdapter(ArrayList<Post> mDataList) {
        this.mPosts = mDataList;
        notifyDataSetChanged();
    }
}
