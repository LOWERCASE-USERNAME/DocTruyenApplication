package com.example.doctruyenapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class LibraryFragment extends Fragment {

    private ImageButton searchButton;
    private TextView viewMoreButton;
    private TextView chapterButton1, chapterButton2, chapterButton3; // Declare these buttons

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        // Lấy danh sách chương từ ChapterData
        List<ChapterData.Chapter> chapters = ChapterData.getChapters();

        // Liên kết nút với các chương truyện
        chapterButton1 = view.findViewById(R.id.chapter_button_1); // Initialize chapterButton1
        chapterButton1.setText(chapters.get(0).getTitle());
        chapterButton1.setOnClickListener(v -> openChapterFragment(chapters.get(0)));

        chapterButton2 = view.findViewById(R.id.chapter_button_2); // Initialize chapterButton2
        chapterButton2.setText(chapters.get(1).getTitle());
        chapterButton2.setOnClickListener(v -> openChapterFragment(chapters.get(1)));

        chapterButton3 = view.findViewById(R.id.chapter_button_3); // Initialize chapterButton3
        chapterButton3.setText(chapters.get(2).getTitle());
        chapterButton3.setOnClickListener(v -> openChapterFragment(chapters.get(2)));

        // Initialize search button and set click listener
        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> openFragment(new SearchFragment()));

        // Initialize view more button and set click listener
        viewMoreButton = view.findViewById(R.id.view_more_button);
        viewMoreButton.setOnClickListener(v -> openFragment(new MoreStoriesFragment()));

        return view;
    }

    // Method to open a chapter fragment with chapter data
    private void openChapterFragment(ChapterData.Chapter chapter) {
        Bundle bundle = new Bundle();
        bundle.putString("chapter_title", chapter.getTitle());
        bundle.putString("chapter_content", chapter.getContent());

        ChapterFragment chapterFragment = new ChapterFragment();
        chapterFragment.setArguments(bundle);

        openFragment(chapterFragment);
    }

    // Method to open a new fragment and replace the current one
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment); // Ensure this ID matches the FrameLayout ID in activity_main.xml
        transaction.addToBackStack(null); // Add to back stack to allow navigation back
        transaction.commit(); // Commit the transaction
    }
}
