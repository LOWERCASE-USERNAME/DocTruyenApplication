package com.example.doctruyenapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.doctruyenapplication.adapter.BookAdapter;
import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListStory_Fragment extends Fragment {

    private static final String ARG_STORY_TYPE = "story_type";
    private GridView listGrid;
    private ArrayList<Book> bookList;
    private BookAdapter bookAdapter;
    private ApiService apiService;

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

        apiService = RetrofitClient.getInstance().create(ApiService.class);
        fetchBooks();

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

    private void fetchBooks() {
        if (getArguments() == null) {
            return;
        }
        String storyType = getArguments().getString(ARG_STORY_TYPE);
        Call<List<Book>> call = apiService.getBooks(0, 10, storyType);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookList = new ArrayList<>(response.body());

                    // Create an adapter and set it to the GridView
                    bookAdapter = new BookAdapter(getActivity(), R.layout.item_book, bookList);
                    listGrid.setAdapter(bookAdapter);

                    // Set up item click listener
                    setupGridViewItemClick();
                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage(), t);
            }
        });
    }

    private void setupGridViewItemClick() {
        listGrid.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            Book selectedBook = bookList.get(position);
            navigateToBookDetail(selectedBook);
        });
    }

    private void navigateToBookDetail(Book book) {
        Intent intent = new Intent(requireContext(), BookDetailActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }

    private void navigateToSearch() {
        Fragment searchFragment = new SearchFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, searchFragment)
                .addToBackStack(null)
                .commit();
    }
}
