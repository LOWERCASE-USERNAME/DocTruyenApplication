package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ListStory_Fragment extends Fragment {

    private static final String ARG_STORY_TYPE = "story_type";

    public static ListStory_Fragment newInstance(String storyType) {
        ListStory_Fragment fragment = new ListStory_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_STORY_TYPE, storyType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liststory, container, false);

        // Initialize toolbar
        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        ImageButton backButton = view.findViewById(R.id.back_button);
        ImageButton searchButton = view.findViewById(R.id.search_button);

        // Set toolbar title based on selected story type
        if (getArguments() != null) {
            String storyType = getArguments().getString(ARG_STORY_TYPE);
            toolbarTitle.setText(storyType);
        }

        // Set up back button click listener
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        // Set up search button click listener
        searchButton.setOnClickListener(v -> navigateToSearch());

        return view;
    }

    private void navigateToSearch() {
        Fragment searchFragment = new SearchFragment(); // Replace with your actual SearchFragment class
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, searchFragment) // Replace with your container ID
                .addToBackStack(null)
                .commit();
    }
}