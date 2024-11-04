package com.example.doctruyenapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.doctruyenapplication.adapter.BookHoriAdapter;
import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.BookReadHistory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookselfFragment extends Fragment {
    private ApiService apiService;
    private BookHoriAdapter bookHoriAdapter;
    private GridView gridView;
    private EditText searchEditText; // Add this
    private List<Book> books; // List of all books
    private List<BookReadHistory> readbooks;
    private List<Book> filteredBooks; // List for filtered books
    private boolean isSelectionMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookself, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        int accountId = sharedPreferences.getInt("accountId", 0);
        if(!isLoggedIn){
            Intent intent = new Intent(requireActivity(), Login.class);
            startActivity(intent);
            requireActivity().finish();
            return null;
        }

        gridView = view.findViewById(R.id.list_stories_grid);
        searchEditText = view.findViewById(R.id.timkiem); // Initialize search EditText

        // Fetch initial data
        apiService = RetrofitClient.getInstance().create(ApiService.class);
        fetchBooks(accountId);

        setupTrashButton(view); // Setup trash button click event

        // Add a text change listener for search functionality
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBooks(s.toString()); // Call filter method when text changes
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void fetchBooks(int accountId) {
        Call<List<BookReadHistory>> call = apiService.getReadBooks(accountId);
        call.enqueue(new Callback<List<BookReadHistory>>() {
            @Override
            public void onResponse(Call<List<BookReadHistory>> call, Response<List<BookReadHistory>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    readbooks = response.body();
                    filteredBooks = new ArrayList<>();
                    for(BookReadHistory readbook : readbooks){
                        filteredBooks.add(readbook.getBook());
                    }
                     // Initialize filtered list
                    bookHoriAdapter = new BookHoriAdapter(requireContext(), R.layout.item_book_hori, filteredBooks);
                    gridView.setAdapter(bookHoriAdapter);
                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<BookReadHistory>> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage(), t);
            }
        });
    }

    private void filterBooks(String query) {
        // Clear the filtered list
        filteredBooks.clear();

        // If query is empty, add all books to filtered list
        if (query.isEmpty()) {
            filteredBooks.addAll(books);
        } else {
            // Filter books based on the search query
            for (Book book : books) {
                if (book.getBookName().toLowerCase().contains(query.toLowerCase())) {
                    filteredBooks.add(book);
                }
            }
        }

        // Notify the adapter about data changes
        bookHoriAdapter.notifyDataSetChanged();
    }

    private void setupTrashButton(View view) {
        ImageButton trashButton = view.findViewById(R.id.delete_button);
        trashButton.setOnClickListener(v -> {
            if (isSelectionMode) {
                showConfirmationDialog(); // Show confirmation dialog to delete
            } else {
                enterSelectionMode(); // Enter selection mode
            }
        });
    }

    private void enterSelectionMode() {
        isSelectionMode = true; // Set selection mode to true
        bookHoriAdapter.setSelectionMode(true); // Enable selection mode in adapter
    }

    private void exitSelectionMode() {
        isSelectionMode = false; // Set selection mode to false
        bookHoriAdapter.setSelectionMode(false); // Disable selection mode in adapter
    }

    private void showConfirmationDialog() {
        List<Book> selectedBooks = bookHoriAdapter.getSelectedBooks(); // Get selected books

        if (selectedBooks.isEmpty()) {
            exitSelectionMode(); // Exit selection mode if no books selected
            return;
        }

        new AlertDialog.Builder(requireContext())
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có muốn xóa những cuốn sách đã chọn không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    bookHoriAdapter.removeSelectedBooks();
                    bookHoriAdapter.clearSelections(); // Clear selections after deletion
                    exitSelectionMode(); // Exit selection mode after deletion
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    exitSelectionMode(); // Exit selection mode if user cancels
                    dialog.dismiss();
                })
                .show();
    }
}
