package com.example.doctruyenapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class LibraryFragment extends Fragment {

    private ImageButton searchButton;
    private TextView viewMoreButton;
    private TextView chapterButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        // Initialize search button and set click listener
        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SearchFragment
                openFragment(new SearchFragment());
            }
        });

        // Initialize view more button and set click listener
        viewMoreButton = view.findViewById(R.id.view_more_button);
        viewMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MoreStoriesFragment
                openFragment(new MoreStoriesFragment());
            }
        });

        // Initialize chapter button and set click listener
        chapterButton = view.findViewById(R.id.chapter_button);
        chapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a bundle with chapter data
                Bundle bundle = new Bundle();
                bundle.putString("chapter_title", "Chapter 1: The Beginning");
                bundle.putString("chapter_content", "This is the content of Chapter 1...");

                // Create the ChapterFragment and pass the bundle
                ChapterFragment chapterFragment = new ChapterFragment();
                chapterFragment.setArguments(bundle);

                // Open the ChapterFragment
                openFragment(chapterFragment);
            }
        });

        return view;
    }

    // Method to open a new fragment and replace the current one
    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment); // Ensure this ID matches the FrameLayout ID in activity_main.xml
        transaction.addToBackStack(null); // Add to back stack to allow navigation back
        transaction.commit(); // Commit the transaction
    }
}
