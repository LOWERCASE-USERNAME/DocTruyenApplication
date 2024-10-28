package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {

    private RecyclerView commentRecyclerView;
    private EditText commentInput;
    private Button sendButton;
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        // Initialize views
        commentRecyclerView = view.findViewById(R.id.comment_recycler_view);
        commentInput = view.findViewById(R.id.comment_input);
        sendButton = view.findViewById(R.id.send_button);

        TextView commentTitle = view.findViewById(R.id.comment_title);
        TextView commentsCount = view.findViewById(R.id.comments_count);
        Spinner sortSpinner = view.findViewById(R.id.sort_spinner);
        ImageButton backButton = view.findViewById(R.id.back_button);

        // Set up RecyclerView
        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentRecyclerView.setAdapter(commentAdapter);

        // Load initial comments
        loadComments();

        // Send button click listener to add new comment
        sendButton.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString().trim();
            if (!commentText.isEmpty()) {
                // Add comment to list and update UI
                Comment newComment = new Comment("User Name", commentText);
                commentList.add(0, newComment); // Add at the top of the list
                commentAdapter.notifyItemInserted(0);
                commentRecyclerView.scrollToPosition(0);
                commentInput.setText(""); // Clear input
            }
        });

        // Back button to navigate back
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        return view;
    }

    // Load initial comments (for example purposes)
    private void loadComments() {
        commentList.add(new Comment("Granny Tân", "Mình cho rằng lợi ích duy nhất..."));
        commentList.add(new Comment("Hwang Long", "Tất cả lập luận và lý luận của bạn..."));
        commentAdapter.notifyDataSetChanged();
    }

    // Comment model class
    private static class Comment {
        String username;
        String content;

        public Comment(String username, String content) {
            this.username = username;
            this.content = content;
        }

        public String getUsername() { return username; }
        public String getContent() { return content; }
    }

    // Adapter for RecyclerView
    private static class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

        private final List<Comment> comments;

        public CommentAdapter(List<Comment> comments) {
            this.comments = comments;
        }

        @NonNull
        @Override
        public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
            Comment comment = comments.get(position);
            holder.usernameTextView.setText(comment.getUsername());
            holder.contentTextView.setText(comment.getContent());
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        public static class CommentViewHolder extends RecyclerView.ViewHolder {
            TextView usernameTextView;
            TextView contentTextView;

            public CommentViewHolder(@NonNull View itemView) {
                super(itemView);
                usernameTextView = itemView.findViewById(R.id.comment_username);
                contentTextView = itemView.findViewById(R.id.comment_content);
            }
        }
    }
}
