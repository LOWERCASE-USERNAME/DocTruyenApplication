package com.example.doctruyenapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Button;

public class ChapterDetailFragment extends Fragment {

    private TextView titleTextView;
    private TextView authorTextView;
    private TextView descriptionTextView;
    private TextView genreTextView;
    private RatingBar ratingBar;
    private TextView chaptersTextView;
    private TextView lastUpdateTextView;
    private ImageButton backButton;
    private ImageButton favoriteButton;
    private Button downloadButton;
    private Button readNowButton;
    private Button followButton;
    private Button storyInfoButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chapter_detail, container, false);

        titleTextView = view.findViewById(R.id.book_title);
        authorTextView = view.findViewById(R.id.book_author);
        descriptionTextView = view.findViewById(R.id.book_description);
        genreTextView = view.findViewById(R.id.book_genre);
        ratingBar = view.findViewById(R.id.book_rating);
        chaptersTextView = view.findViewById(R.id.book_chapters);
        lastUpdateTextView = view.findViewById(R.id.last_update);
        backButton = view.findViewById(R.id.back_button);
        favoriteButton = view.findViewById(R.id.favorite_button);
        downloadButton = view.findViewById(R.id.download_button);
        readNowButton = view.findViewById(R.id.read_now_button);
        followButton = view.findViewById(R.id.follow_button);
        storyInfoButton = view.findViewById(R.id.chapter_list_button);

        Bundle arguments = getArguments();
        if (arguments != null) {
            titleTextView.setText(arguments.getString("chapter_title"));
            authorTextView.setText(arguments.getString("chapter_author"));
//            descriptionTextView.setText(arguments.getString("chapter_description"));
            // Set other fields as needed
        }

        storyInfoButton = view.findViewById(R.id.chapter_list_button);
        storyInfoButton.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.container, new StoryInfoFragment())
                    .addToBackStack(null)
                    .commit();
        });
        backButton.setOnClickListener(v -> getActivity().onBackPressed());
        favoriteButton.setOnClickListener(v -> {
            // Handle favorite action
        });
        downloadButton.setOnClickListener(v -> {
            // Handle download action
        });
        readNowButton.setOnClickListener(v -> openChapterFragment());

        followButton.setOnClickListener(v -> {
            // Handle follow action
        });

        return view;
    }

    private void openChapterFragment() {
        ChapterFragment chapterFragment = new ChapterFragment();
        // Pass any necessary data using a bundle
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, chapterFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}