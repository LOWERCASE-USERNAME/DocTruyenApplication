package com.example.doctruyenapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.doctruyenapplication.adapter.BookHoriAdapter;
import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookselfFragment extends Fragment {
    private ApiService apiService;
    private BookHoriAdapter bookHoriAdapter;
    private GridView gridView;
    private boolean isSelectionMode = false;
    List<Book> books;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookself, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if(!isLoggedIn){
            Intent intent = new Intent(requireActivity(), Login.class);
            startActivity(intent);
            requireActivity().finish();
            return null;
        }

        gridView = view.findViewById(R.id.list_stories_grid);

        //fetch initial data
        apiService = RetrofitClient.getInstance().create(ApiService.class);
        fetchBooks();

        setupTrashButton(view); // Setup trash button click event

        return view;
    }

    private void fetchBooks() {
        Call<List<Book>> call = apiService.getAllBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    books = response.body();
                    for(Book b : response.body()){
//                        Log.d("Retrofit", b.getChapters());
                    }
                    bookHoriAdapter = new BookHoriAdapter(requireContext(), R.layout.item_book_hori, books);
                    gridView.setAdapter(bookHoriAdapter);
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

    private void setupTrashButton(View view) {
        ImageButton trashButton = view.findViewById(R.id.delete_button);
        trashButton.setOnClickListener(v -> {
            if (isSelectionMode) {
                // If in selection mode, show confirmation dialog to delete
                showConfirmationDialog();
            } else {
                // If not in selection mode, enter selection mode
                enterSelectionMode();
            }
        });
    }

    private void enterSelectionMode() {
        isSelectionMode = true; // Set selection mode to true
        bookHoriAdapter.setSelectionMode(true); // Enable selection mode in adapter
        // Optionally, change the button appearance or text here
    }

    private void exitSelectionMode() {
        isSelectionMode = false; // Set selection mode to false
        bookHoriAdapter.setSelectionMode(false); // Disable selection mode in adapter
        // Optionally, change the button appearance or text back to normal here
    }

    private void showConfirmationDialog() {
        // Get selected books
        List<Book> selectedBooks = bookHoriAdapter.getSelectedBooks();

        // Check if any books are selected
        if (selectedBooks.isEmpty()) {
            // If no books are selected, exit selection mode
            exitSelectionMode();
            return; // Exit if no books are selected
        }

        // Show confirmation dialog
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