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
    private List<Book> books;            // List for displaying books (filtered)
    private List<Book> originalBooks;    // Original list of all books
    private List<Boolean> selectedItems;
    private boolean isSelectionMode = false;

    public BookHoriAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource, objects);
        this.context = context;
        this.books = new ArrayList<>(objects);        // Filtered list reference
        this.originalBooks = new ArrayList<>(objects); // Original list for restoring items
        this.selectedItems = new ArrayList<>(objects.size());
        for (int i = 0; i < objects.size(); i++) {
            selectedItems.add(false);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position >= books.size()) {
            return new View(context);  // Safeguard for out-of-bounds access
        }

        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_book_hori, parent, false);
            holder = new ViewHolder();
            holder.bookTitle = convertView.findViewById(R.id.book_title);
            holder.bookChapter = convertView.findViewById(R.id.book_chapter);
            holder.bookImage = convertView.findViewById(R.id.book_image);
            holder.bookCheckbox = convertView.findViewById(R.id.book_checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Book book = books.get(position);
        if (holder.bookTitle != null) holder.bookTitle.setText(book.getBookName());

        List<Chapter> chapters = book.getChapters();
        if (holder.bookChapter != null && chapters != null && !chapters.isEmpty()) {
            Chapter newestChapter = chapters.get(chapters.size() - 1);
            holder.bookChapter.setText(String.format("Chapter %d: %s", newestChapter.getChapterOrder(), newestChapter.getChapterName()));
        } else {
            holder.bookChapter.setText("");
        }

        if (holder.bookImage != null) {
            Glide.with(context).load(book.getPictureLink()).into(holder.bookImage);
        }

        convertView.setBackgroundColor(position % 2 == 0 ? 0xFFEFEFEF : 0xFFFFFFFF);
        holder.bookCheckbox.setVisibility(isSelectionMode ? View.VISIBLE : View.GONE);
        holder.bookCheckbox.setChecked(selectedItems.get(position));

//        convertView.setOnClickListener(v -> {
//            if (isSelectionMode) {
//                boolean isSelected = !selectedItems.get(position);
//                selectedItems.set(position, isSelected);
//                holder.bookCheckbox.setChecked(isSelected);
//                notifyDataSetChanged();
//            }
//        });

        return convertView;
    }

    public List<Boolean> getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(List<Boolean> selectedItems) {
        this.selectedItems = selectedItems;
    }

    public boolean isSelectionMode() {
        return isSelectionMode;
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

    public void updateData(List<Book> newBooks) {
        this.originalBooks.clear();
        this.originalBooks.addAll(newBooks);
        this.books.clear();
        this.books.addAll(newBooks);
        notifyDataSetChanged();
    }

    public void filterBooks(String query) {
        String searchQuery = query.toUpperCase();
        books.clear();

        for (Book book : originalBooks) {
            if (book.getBookName().toUpperCase().contains(searchQuery)) {
                books.add(book);
            }
        }

        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView bookTitle;
        TextView bookChapter;
        ImageView bookImage;
        CheckBox bookCheckbox;
    }
}
