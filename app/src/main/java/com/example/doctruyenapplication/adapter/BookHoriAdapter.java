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
import com.example.doctruyenapplication.object.Chapter;

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
        initializeSelectionList(objects.size());
    }

    private void initializeSelectionList(int size) {
        this.selectedItems = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            selectedItems.add(false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_book_hori, parent, false);
        }

        Book book = books.get(position);
        TextView bookTitle = convertView.findViewById(R.id.book_title);
        TextView bookChapter = convertView.findViewById(R.id.book_chapter);
        ImageView bookImage = convertView.findViewById(R.id.book_image);
        CheckBox bookCheckbox = convertView.findViewById(R.id.book_checkbox);

        bookTitle.setText(book.getBookName());

        // Display the newest chapter if available
        List<Chapter> chapters = book.getChapters();
        if (chapters != null && !chapters.isEmpty()) {
            Chapter newestChapter = chapters.get(chapters.size() - 1);
            bookChapter.setText(String.format("Chapter %d: %s", newestChapter.getChapterOrder(), newestChapter.getChapterName()));
        } else {
            bookChapter.setText("No chapters available");
        }

        Glide.with(context).load(book.getPictureLink()).into(bookImage);

        // Set alternating background colors
        convertView.setBackgroundColor(position % 2 == 0 ? 0xFFEFEFEF : 0xFFFFFFFF);

        if (isSelectionMode) {
            bookCheckbox.setVisibility(View.VISIBLE);
            bookCheckbox.setChecked(selectedItems.get(position));
        } else {
            bookCheckbox.setVisibility(View.GONE);
        }

        // Handle selection toggle
        convertView.setOnClickListener(v -> {
            if (isSelectionMode) {
                boolean isSelected = !selectedItems.get(position);
                selectedItems.set(position, isSelected);
                bookCheckbox.setChecked(isSelected);
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
        initializeSelectionList(books.size());
        notifyDataSetChanged();
    }

    public void updateData(List<Book> newBooks) {
        books.clear();
        books.addAll(newBooks);
        initializeSelectionList(newBooks.size());
        notifyDataSetChanged();
    }

    public void filterBooks(String query) {
        String searchQuery = query.toUpperCase();
        List<Book> filteredList = new ArrayList<>();

        for (Book book : books) {
            if (book.getBookName().toUpperCase().contains(searchQuery)) {
                filteredList.add(book);
            }
        }

        clear();
        addAll(filteredList);
        initializeSelectionList(filteredList.size());
        notifyDataSetChanged();
    }
}
