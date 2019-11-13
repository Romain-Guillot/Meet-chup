package com.progmobile.meetchup.ui.event_view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Post;
import com.progmobile.meetchup.models.User;
import com.progmobile.meetchup.ui.post_view.PostViewActivity;
import com.progmobile.meetchup.utils.DownloadImage;

import java.util.List;


/**
 *
 */
public class PostsListViewAdapter extends RecyclerView.Adapter<PostsListViewAdapter.PostViewHolder> {

    List<Post> posts;

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView descriptionView;
        ImageView imageView;
        TextView userView;
        PostViewHolder(View itemView) {
            super(itemView);
            this.descriptionView = itemView.findViewById(R.id.item_post_description);
            this.imageView = itemView.findViewById(R.id.item_post_image);
            this.userView = itemView.findViewById(R.id.item_post_user);
        }
    }


    public PostsListViewAdapter(List<Post> posts) {
        this.posts = posts;
    }


    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_post, parent, false);
        PostViewHolder holder = new PostViewHolder(postView);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);

        String description = post.getDescription();
        if (description != null)
            holder.descriptionView.setText(post.getDescription());

        String imageURL = post.getDocument().getUrl();
        if (imageURL != null) {
            new DownloadImage(holder.imageView).execute(imageURL);
        }

        User user = post.getUser();
        String name = user.getName();
        if (name != null)
            holder.userView.setText(name);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), PostViewActivity.class);
            intent.putExtra(PostViewActivity.EXTRA_POST_ID, post.getId());
            holder.itemView.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

}
