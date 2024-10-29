package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.doctruyenapplication.adapter.BookAdapter;
import com.example.doctruyenapplication.object.Book;

import java.util.ArrayList;

public class ListStory_Fragment extends Fragment {

    private static final String ARG_STORY_TYPE = "story_type";
    private GridView listGrid;
    private ArrayList<Book> bookList;
    private BookAdapter bookAdapter;

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
        listGrid = view.findViewById(R.id.list_stories_grid);
        bookList = new ArrayList<>();
        bookList.add(new Book("https://cdn.myanimelist.net/images/anime/12/39497.jpg", "Boku no Pico", "Chapter 1"));
        bookList.add(new Book("https://a.pinatafarm.com/500x377/d972ce254e/2-gay-black-mens-kissing.jpg", "Two black man kissing", "Chapter 2"));
        bookList.add(new Book("https://ih1.redbubble.net/image.4595760308.2867/flat,750x,075,f-pad,750x1000,f8f8f8.jpg", "Why are you gay", "Chapter 3"));

        // Create an adapter and set it to the GridView
        bookAdapter = new BookAdapter(getActivity(), R.layout.item_book, bookList);
        listGrid.setAdapter(bookAdapter);
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