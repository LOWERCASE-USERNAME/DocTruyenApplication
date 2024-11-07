package com.example.doctruyenapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFragment extends Fragment {
    private ApiService apiService;
    private RecyclerView commentRecyclerView;
    private EditText commentInput;
    private Button sendButton;
    private List<Comment> commentList;
    private CommentAdapter commentAdapter;
    private int bookId; // Store bookId for fetching and adding comments
    private Book book;

    public CommentFragment(Book book) {
        this.book = book;
    }

    public CommentFragment(int bookId) {
        this.bookId = bookId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        // Initialize ApiService
        apiService = RetrofitClient.getInstance().create(ApiService.class);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);

        int accountId = sharedPreferences.getInt("accountId", 0);
        Bundle bundle = getArguments();
//        if (bundle != null) {
//
//            bookId = bundle.getInt("bookId");
//        }

        // Initialize views
        commentRecyclerView = view.findViewById(R.id.comment_recycler_view);
        commentInput = view.findViewById(R.id.comment_input);
        sendButton = view.findViewById(R.id.send_button);

        // Additional UI components
//        TextView commentTitle = view.findViewById(R.id.comment_title);
//        TextView commentsCount = view.findViewById(R.id.comments_count);
        Spinner sortSpinner = view.findViewById(R.id.sort_spinner);
        ImageButton backButton = view.findViewById(R.id.back_button);

        // Set up RecyclerView
        commentList = new ArrayList<>();
//        commentAdapter = new CommentAdapter(commentList);
//        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        commentRecyclerView.setAdapter(commentAdapter);

        // Load initial comments
        loadComments(book != null ? book.getBookId() : bookId);

        // Send button click listener to add a new comment
        sendButton.setOnClickListener(v -> {
            String commentText = commentInput.getText().toString().trim();
            if (!commentText.isEmpty()) {
                addComment(book != null ? book.getBookId() : bookId, commentText,accountId);
            }
        });

        // Back button to navigate back
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    // Load initial comments from API
    private void loadComments(int bookId) {
        Call<List<Comment>> call = apiService.getComments(bookId);
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    commentList.clear();
                    commentList.addAll(response.body());
                    commentAdapter = new CommentAdapter(commentList);
                    commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    commentRecyclerView.setAdapter(commentAdapter);
//                    commentAdapter.notifyDataSetChanged();
                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage(), t);
            }
        });
    }

    // Add a new comment through API
    private void addComment(int bookId, String content,int accountId) {
        Comment newComment = new Comment();
        newComment.setBookId(bookId);
        newComment.setContent(content);
        newComment.setCommentId(0);

        Call<Void> call = apiService.addComment(newComment,accountId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Add new comment locally for immediate UI update
                    commentList.add(0, newComment);
                    commentAdapter.notifyItemInserted(0);
                    commentRecyclerView.scrollToPosition(0);
                    commentInput.setText("");
                } else {
                    Log.e("Retrofit", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage(), t);
            }
        });
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
//            holder.usernameTextView.setText(comment.getUserName()); // Make sure Comment class has getUsername()
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
