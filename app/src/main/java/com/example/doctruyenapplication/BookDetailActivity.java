package com.example.doctruyenapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.doctruyenapplication.object.Book;

public class BookDetailActivity extends AppCompatActivity {
    private Book book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        if (getIntent() != null && getIntent().hasExtra("book")) {
            book = (Book) getIntent().getSerializableExtra("book");
        }

        if (savedInstanceState == null) {
            replaceFragment(BookDetailFragment.newInstance(book));
        }

        findViewById(R.id.back_button).setOnClickListener(v -> this.onBackPressed());
        findViewById(R.id.favorite_button).setOnClickListener(v -> {
            // Handle favorite action
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.book_detail_container, fragment);
        transaction.commit();
    }

    public void replaceWithChapterFragment(ChapterFragment chapterFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.book_detail_container, chapterFragment)
                .addToBackStack(null)
                .commit();
    }
}