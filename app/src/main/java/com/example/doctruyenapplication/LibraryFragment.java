package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.doctruyenapplication.adapter.BookAdapter;
import com.example.doctruyenapplication.object.Book;
import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    private GridView newStoriesGridView, bestStoriesGridView, anStoriesGridView;
    private ArrayList<Book> bookList;
    private BookAdapter bookAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        // Initialize GridView
        newStoriesGridView = view.findViewById(R.id.new_stories_grid);
        bestStoriesGridView = view.findViewById(R.id.best_stories_grid);
        anStoriesGridView = view.findViewById(R.id.an_stories_grid);

        // Initialize book list and add some sample data (you can replace this with actual data)
        bookList = new ArrayList<>();
        bookList.add(new Book("https://cdn.myanimelist.net/images/anime/12/39497.jpg", "Boku no Pico", "Chapter 1"));
        bookList.add(new Book("https://a.pinatafarm.com/500x377/d972ce254e/2-gay-black-mens-kissing.jpg", "Two black man kissing", "Chapter 2"));
        bookList.add(new Book("https://ih1.redbubble.net/image.4595760308.2867/flat,750x,075,f-pad,750x1000,f8f8f8.jpg", "Why are you gay", "Chapter 3"));

        // Create an adapter and set it to the GridView
        bookAdapter = new BookAdapter(getActivity(), R.layout.item_book, bookList);
        newStoriesGridView.setAdapter(bookAdapter);
        bestStoriesGridView.setAdapter(bookAdapter);
        anStoriesGridView.setAdapter(bookAdapter);

        // Set up click listeners
        setupClickListeners(view);

        return view;
    }

    private void setupClickListeners(View view) {
        ImageButton searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> navigateToFragment(new SearchFragment()));

        TextView viewMoreNewStories = view.findViewById(R.id.view_more_button);
        viewMoreNewStories.setOnClickListener(v -> navigateToFragment(new MoreStoriesFragment()));

        TextView viewMoreBestStories = view.findViewById(R.id.view_more_button1);
        viewMoreBestStories.setOnClickListener(v -> navigateToFragment(new MoreStoriesFragment()));

        TextView viewMoreAwardStories = view.findViewById(R.id.view_more_button2);
        viewMoreAwardStories.setOnClickListener(v -> navigateToFragment(new MoreStoriesFragment()));
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment); // Replace with your container ID
        fragmentTransaction.addToBackStack(null); // Optional: add to back stack
        fragmentTransaction.commit();
    }
}