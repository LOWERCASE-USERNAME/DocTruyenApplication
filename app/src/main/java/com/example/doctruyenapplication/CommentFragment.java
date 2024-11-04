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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyenapplication.CommentApi;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.Comment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {

    private RecyclerView commentRecyclerView;
    private EditText commentInput;
    private Button sendButton;
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    private CommentApi commentAPI;

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

        // Initialize API client
        commentAPI = RetrofitClient.getInstance().create(CommentApi.class);

        // Load initial comments from API
        loadComments();

        // Send button click listener to add new comment
        sendButton.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString().trim();
            if (!commentText.isEmpty()) {
                // Tạo comment mới và gọi API để lưu vào database
                Comment newComment = new Comment("User Name", commentText); // Add other fields as necessary
                postComment(newComment);
                commentInput.setText(""); // Clear input
            }
        });

        // Back button to navigate back
        backButton.setOnClickListener(v -> getActivity().onBackPressed());

        return view;
    }

    // Load initial comments from API
    private void loadComments() {
        int storyId = 1; // Replace with the actual story ID
        commentAPI.getComments(storyId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commentList.clear();
                    commentList.addAll(response.body());
                    commentAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Failed to load comments", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Post new comment to API
    private void postComment(Comment comment) {
        commentAPI.postComment(comment).enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Add comment to list and update UI
                    commentList.add(0, response.body());
                    commentAdapter.notifyItemInserted(0);
                    commentRecyclerView.scrollToPosition(0);
                } else {
                    Toast.makeText(getContext(), "Failed to post comment", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
