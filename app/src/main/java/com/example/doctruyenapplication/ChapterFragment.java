package com.example.doctruyenapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChapterFragment extends Fragment {

    private TextView chapterTitle;
    private TextView chapterContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter, container, false);

        // Initialize UI components
        chapterTitle = view.findViewById(R.id.chapter_title);
        chapterContent = view.findViewById(R.id.chapter_content);

        // Get arguments passed from the previous fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("chapter_title", "No Title");
            String content = bundle.getString("chapter_content", "No Content");

            // Set the chapter title and content
            chapterTitle.setText(title);
            chapterContent.setText(content);
        }

        return view;
    }
}
