package com.example.doctruyenapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
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
        CheckBox bookCheckbox = convertView.findViewById(R.id.book_checkbox);

        bookTitle.setText(book.getBookName());
//        bookChapter.setText(book.getChapters());
        Glide.with(context).load(book.getPictureLink()).into(bookImage);

        if (position % 2 == 0) {
            convertView.setBackgroundColor(0xFFEFEFEF);
        } else {
            convertView.setBackgroundColor(0xFFFFFFFF);
        }

        // Change the background color based on selection
        if (isSelectionMode) {
            bookCheckbox.setVisibility(View.VISIBLE);
//            convertView.setBackgroundColor(selectedItems.get(position) ? 0xFFDDDDDD : (position % 2 == 0 ? 0xFFEFEFEF : 0xFFFFFFFF));
        }else{
            bookCheckbox.setVisibility(View.GONE);
            bookCheckbox.setChecked(false);
        }

        // Handle clicks differently based on selection mode
        convertView.setOnClickListener(v -> {
            if (isSelectionMode) {
                // Toggle selection state
                bookCheckbox.setChecked(!selectedItems.get(position));
                selectedItems.set(position, !selectedItems.get(position));
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public void setSelectionMode(boolean selectionMode) {
        this.isSelectionMode = selectionMode;
        if (!selectionMode) {
            clearSelections();
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

    public void filterBooks(String query) {
        String searchQuery = query.toUpperCase();
        List<Book> filteredList = new ArrayList<>();

        for (Book book : books) {
            String bookName = book.getBookName().toUpperCase();
            if (bookName.contains(searchQuery)) {
                filteredList.add(book);
            }
        }

        clear();
        addAll(filteredList);
        notifyDataSetChanged();
    }
}