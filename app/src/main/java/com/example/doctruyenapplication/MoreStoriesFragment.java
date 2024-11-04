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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class MoreStoriesFragment extends Fragment {

    private GridView storiesGridView;
    private BookAdapter bookAdapter;
    private ArrayList<Book> bookList;
    private ApiService apiService;
    private String storyCategory;

    public static MoreStoriesFragment newInstance(String category) {
        MoreStoriesFragment fragment = new MoreStoriesFragment();
        Bundle args = new Bundle();
        args.putString("CATEGORY", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_morestories, container, false);

        if (getArguments() != null) {
            storyCategory = getArguments().getString("CATEGORY", "Truyện mới");
        }

        TextView toolbarTitle = view.findViewById(R.id.toolbar_title);
        ImageButton backButton = view.findViewById(R.id.back_button);
        ImageButton searchButton = view.findViewById(R.id.search_button);
        storiesGridView = view.findViewById(R.id.list_more_stories_grid);

        toolbarTitle.setText(storyCategory);

        // Set up back button click listener
        backButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        // Set up search button click listener (navigate to SearchFragment)
        searchButton.setOnClickListener(v -> navigateToSearch());

        // Initialize API service and fetch books
        apiService = RetrofitClient.getInstance().create(ApiService.class);
        fetchBooksByCategory(storyCategory);

        // Set item click listener to navigate to book info
        storiesGridView.setOnItemClickListener((parent, view1, position, id) -> {
            Book selectedBook = bookList.get(position);
            navigateToBookInfo(selectedBook);
        });

        return view;
    }

    private void fetchBooksByCategory(String category) {
        Call<List<Book>> call = apiService.getBooks(0, 10, category);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(@NonNull Call<List<Book>> call, @NonNull Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookList = new ArrayList<>(response.body());
                    bookAdapter = new BookAdapter(getActivity(), R.layout.item_book, bookList);
                    storiesGridView.setAdapter(bookAdapter);
                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Book>> call, @NonNull Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage(), t);
            }
        });
    }

    private void navigateToSearch() {
        Fragment searchFragment = new SearchFragment();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, searchFragment) // Replace with actual container ID
                .addToBackStack(null)
                .commit();
    }

    private void navigateToBookInfo(Book book) {
        Intent intent = new Intent(requireContext(), BookDetailActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }
}
