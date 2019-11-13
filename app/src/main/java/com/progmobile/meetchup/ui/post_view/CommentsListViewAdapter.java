package com.progmobile.meetchup.ui.post_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.progmobile.meetchup.R;
import com.progmobile.meetchup.models.Comment;
import com.progmobile.meetchup.models.User;

import java.util.List;

public class CommentsListViewAdapter extends RecyclerView.Adapter<CommentsListViewAdapter.CommentViewHolder> {

    List<Comment> comments;

    public CommentsListViewAdapter(List<Comment> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentsListViewAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View commentView = inflater.inflate(R.layout.item_comment, parent, false);
        CommentsListViewAdapter.CommentViewHolder holder = new CommentsListViewAdapter.CommentViewHolder(commentView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsListViewAdapter.CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);

        User user = comment.getUser();
        String name = user.getName();
        if (name != null)
            holder.userView.setText(name);

        String commentText = comment.getComment();
        if (commentText != null) {
            holder.commentView.setText(comment.getComment());
        }
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView userView;
        TextView commentView;

        CommentViewHolder(View itemView) {
            super(itemView);
            this.userView = itemView.findViewById(R.id.item_comment_user);
            this.commentView = itemView.findViewById(R.id.item_comment_comment);
        }
    }


}
