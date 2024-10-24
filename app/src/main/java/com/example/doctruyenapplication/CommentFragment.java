package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {

    private TextView chapterTitle;
    private RecyclerView commentRecyclerView;
    private CommentAdapter commentAdapter;
    private List<String> commentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        // Initialize UI components
        chapterTitle = view.findViewById(R.id.chapter_title);
        commentRecyclerView = view.findViewById(R.id.comment_recycler_view);

        // Get the chapter title passed from the previous fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("chapter_title", "No Title");
            chapterTitle.setText("Bình luận cho: " + title);
        }

        // Initialize comment list (this should be fetched from the database or API in a real scenario)
        commentList = new ArrayList<>();
        commentList.add("Bình luận 1...");
        commentList.add("Bình luận 2...");
        commentList.add("Bình luận 3...");

        // Setup RecyclerView with CommentAdapter
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentAdapter = new CommentAdapter(commentList);
        commentRecyclerView.setAdapter(commentAdapter);

        return view;
    }
}
