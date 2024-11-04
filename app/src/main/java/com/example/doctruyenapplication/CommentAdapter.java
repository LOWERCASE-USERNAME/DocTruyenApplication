package com.example.doctruyenapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyenapplication.R;
import com.example.doctruyenapplication.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private List<Comment> commentList;

    // Constructor
    public CommentAdapter(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each comment item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        // Bind data to each item view
        Comment comment = commentList.get(position);
        holder.usernameTextView.setText(comment.getUsername());
        holder.contentTextView.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    // ViewHolder class to hold item views
    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView, contentTextView;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.username_text_view);
            contentTextView = itemView.findViewById(R.id.content_text_view);
        }
    }
}
