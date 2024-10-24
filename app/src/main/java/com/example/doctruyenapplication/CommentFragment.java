package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentFragment extends Fragment {




    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private List<String> commentList;
    private EditText commentInput;
    private Button submitButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        // Initialize the comment list
        commentList = new ArrayList<>();

        // Initialize the RecyclerView
        commentRecyclerView = view.findViewById(R.id.comment_recycler_view);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the CommentAdapter
        commentAdapter = new CommentAdapter(commentList);
        commentRecyclerView.setAdapter(commentAdapter);

        // Initialize the EditText and Button for submitting comments
        commentInput = view.findViewById(R.id.comment_input);
        submitButton = view.findViewById(R.id.submit_button);

        // Set up the submit button click listener
        submitButton.setOnClickListener(v -> {
            String newComment = commentInput.getText().toString().trim();
            if (!newComment.isEmpty()) {
                // Add the new comment to the list and clear the input field
                addComment(newComment);
                commentInput.setText(""); // Clear the input field
            } else {
                Toast.makeText(getContext(), "Please enter a comment", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // Method to add a new comment and update the RecyclerView
    private void addComment(String comment) {
        commentList.add(0, comment); // Add new comment at the beginning (for reverse order)
        commentAdapter.notifyItemInserted(0); // Notify the adapter of the new item
        commentRecyclerView.scrollToPosition(0); // Scroll to the top to show the new comment
    }
}