package com.example.doctruyenapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doctruyenapplication.object.Book;

public class BookDetailTabAdapter extends FragmentStateAdapter {
    private Book book;
    public BookDetailTabAdapter(@NonNull Fragment fragment, Book book) {
        super(fragment);
        this.book = book;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new BookDescriptionFragment(book.getDescription());
            case 1:
                return new RatingReviewFragment();
            case 2:
                return new CommentFragment();
            case 3:
                return new ChapterListFragment(book.getChapters());
            default:
                return new BookDescriptionFragment(book.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}