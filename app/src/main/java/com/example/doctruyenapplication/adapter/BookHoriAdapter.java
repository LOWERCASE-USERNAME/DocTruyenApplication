package com.example.doctruyenapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doctruyenapplication.R;
import com.example.doctruyenapplication.object.Book;

import java.util.ArrayList;
import java.util.List;

public class BookHoriAdapter extends ArrayAdapter<Book> {
    private Context context;
    private List<Book> books;
    private List<Boolean> selectedItems;
    private boolean isSelectionMode = false;

    public BookHoriAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource, objects);
        this.context = context;
        this.books = new ArrayList<>(objects);
        this.selectedItems = new ArrayList<>(objects.size());
        for (int i = 0; i < objects.size(); i++) {
            selectedItems.add(false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_book_hori, parent, false);
        }

        Book book = this.books.get(position);
        TextView bookTitle = convertView.findViewById(R.id.book_title);
        TextView bookChapter = convertView.findViewById(R.id.book_chapter);
        ImageView bookImage = convertView.findViewById(R.id.book_image);

        bookTitle.setText(book.getBookname());
        bookChapter.setText(book.getBookchap());
        Glide.with(context).load(book.getLink()).into(bookImage);

        // Alternate background colors
        if (position % 2 == 0) {
            convertView.setBackgroundColor(0xFFEFEFEF); // Light gray
        } else {
            convertView.setBackgroundColor(0xFFFFFFFF); // White
        }

        // Change the background color based on selection
        if (isSelectionMode) {
            convertView.setBackgroundColor(selectedItems.get(position) ? 0xFFDDDDDD : (position % 2 == 0 ? 0xFFEFEFEF : 0xFFFFFFFF));
        }

        // Handle clicks differently based on selection mode
        convertView.setOnClickListener(v -> {
            if (isSelectionMode) {
                // Toggle selection state
                selectedItems.set(position, !selectedItems.get(position));
                notifyDataSetChanged();
            } else {
                // Navigate to book details
                // Intent intent = new Intent(context, BookDetailActivity.class);
                // intent.putExtra("bookId", book.getId());
                // context.startActivity(intent);
            }
        });

        return convertView;
    }

    public void setSelectionMode(boolean selectionMode) {
        this.isSelectionMode = selectionMode;
        if (!selectionMode) {
            clearSelections(); // Clear selections when exiting selection mode
        }
        notifyDataSetChanged();
    }

    public List<Book> getSelectedBooks() {
        List<Book> selectedBooks = new ArrayList<>();
        for (int i = 0; i < selectedItems.size(); i++) {
            if (selectedItems.get(i)) {
                selectedBooks.add(books.get(i));
            }
        }
        return selectedBooks;
    }

    public void removeSelectedBooks() {
        for (int i = selectedItems.size() - 1; i >= 0; i--) {
            if (selectedItems.get(i)) {
                books.remove(i);
                selectedItems.remove(i);
            }
        }
        notifyDataSetChanged();
    }

    public void clearSelections() {
        for (int i = 0; i < selectedItems.size(); i++) {
            selectedItems.set(i, false);
        }
        notifyDataSetChanged();
    }
    public void updateData(List<Book> books) {
        this.books.clear();
        this.books.addAll(books);
        notifyDataSetChanged();
    }

}