package com.example.doctruyenapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.doctruyenapplication.R;
import com.example.doctruyenapplication.adapter.BookHoriAdapter;
import com.example.doctruyenapplication.object.Book;

import java.util.ArrayList;
import java.util.List;

public class BookselfFragment extends Fragment {
    private BookHoriAdapter bookHoriAdapter;
    private GridView gridView;
    private boolean isSelectionMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookself, container, false);
        gridView = view.findViewById(R.id.list_stories_grid);

        List<Book> books = loadBooks(); // Load book list
        bookHoriAdapter = new BookHoriAdapter(requireContext(), R.layout.item_book_hori, books);
        gridView.setAdapter(bookHoriAdapter);

        setupTrashButton(view); // Setup trash button click event

        return view;
    }

    private List<Book> loadBooks() {
        // Sample code to load books
        List<Book> books = new ArrayList<>();
        books.add(new Book("https://cdn.myanimelist.net/images/anime/12/39497.jpg", "Boku no Pico", "Chapter 1"));
        books.add(new Book("https://a.pinatafarm.com/500x377/d972ce254e/2-gay-black-mens-kissing.jpg", "Two black men kissing", "Chapter 2"));
        books.add(new Book("https://ih1.redbubble.net/image.4595760308.2867/flat,750x,075,f-pad,750x1000,f8f8f8.jpg", "Why are you gay?", "Chapter 3"));
        books.add(new Book("https://dientusangtaovn.com/wp-content/uploads/2023/03/an-ba-to-com.jpg", "An ba to com", "Chapter 69"));
        books.add(new Book("https://oladino.com/wp-content/uploads/2024/09/diddy-be-out-here-slicker-than-ever-funny-diddy-baby-oil-meme-png-280924013-800x800.jpg", "How to yummy in Diddy's party", "Chapter 500"));
        books.add(new Book("https://content.imageresizer.com/images/memes/Emo-Hitler-meme-8.jpg", "100 recipes for frying Jews", "Chapter 2"));
        books.add(new Book("https://ih1.redbubble.net/image.3211247098.8237/bg,f8f8f8-flat,750x,075,f-pad,750x1000,f8f8f8.jpg", "Be bited by spider, I become the best Orphan", "Chapter 5"));
        books.add(new Book("https://i.pinimg.com/736x/36/45/d5/3645d52b294d4b1d585c972eb4c05558.jpg", "Isekai to slave, I suprisingly become best Nigga", "Chapter 35"));


        return books;
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