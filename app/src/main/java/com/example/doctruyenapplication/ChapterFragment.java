package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class ChapterFragment extends Fragment {

    private TextView chapterTitle;
    private TextView chapterContent;
    private Button nextChapterButton;
    private Button previousChapterButton;
    private Button commentButton;
    private ScrollView scrollView;

    private int currentChapterIndex = 0;  // Start at the first chapter (index 0)
    private List<ChapterData.Chapter> chapters;  // Hold the list of chapters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter, container, false);

        // Initialize UI components
        chapterTitle = view.findViewById(R.id.chapter_title);
        chapterContent = view.findViewById(R.id.chapter_content);
        nextChapterButton = view.findViewById(R.id.next_chapter_button);
        previousChapterButton = view.findViewById(R.id.previous_chapter_button);
        commentButton = view.findViewById(R.id.comment_button);
        scrollView = view.findViewById(R.id.scroll_view);

        // Get the list of chapters from ChapterData
        chapters = ChapterData.getChapters();

        // Get arguments passed from the previous fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            String title = bundle.getString("chapter_title", chapters.get(0).getTitle());
            String content = bundle.getString("chapter_content", chapters.get(0).getContent());

            // Set the initial chapter title and content based on the passed data
            chapterTitle.setText(title);
            chapterContent.setText(content);
        } else {
            // Load the first chapter by default
            loadChapter(currentChapterIndex);
        }

        // Setup next and previous chapter buttons
        nextChapterButton.setOnClickListener(v -> {
            // Load next chapter, but only if it's not the last chapter
            if (currentChapterIndex < chapters.size() - 1) {
                loadChapter(++currentChapterIndex);
            }
        });

        previousChapterButton.setOnClickListener(v -> {
            // Load previous chapter, but only if it's not the first chapter
            if (currentChapterIndex > 0) {
                loadChapter(--currentChapterIndex);
            }
        });

        // Setup comment button
        commentButton.setOnClickListener(v -> openCommentFragment());

        return view;
    }

    // Method to load a new chapter from ChapterData
    private void loadChapter(int chapterIndex) {
        ChapterData.Chapter chapter = chapters.get(chapterIndex);  // Get the chapter data
        chapterTitle.setText(chapter.getTitle());
        chapterContent.setText(chapter.getContent());

        // Scroll back to the top of the scroll view when the new chapter is loaded
        scrollView.scrollTo(0, 0);
    }

    // Method to open the CommentFragment
    private void openCommentFragment() {
        CommentFragment commentFragment = new CommentFragment();

        // Pass the current chapter's title to the CommentFragment
        Bundle bundle = new Bundle();
        bundle.putString("chapter_title", chapterTitle.getText().toString());
        commentFragment.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, commentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
