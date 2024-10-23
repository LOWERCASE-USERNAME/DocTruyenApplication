package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.fragment.app.Fragment;
import com.example.doctruyenapplication.adapter.BookAdapter;
import com.example.doctruyenapplication.object.Book;
import java.util.ArrayList;

public class LibraryFragment extends Fragment {

    private GridView newStoriesGridView;
    private ArrayList<Book> bookList;
    private BookAdapter bookAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        // Initialize GridView
        newStoriesGridView = view.findViewById(R.id.new_stories_grid);

        // Initialize book list and add some sample data (you can replace this with actual data)
        bookList = new ArrayList<>();
        bookList.add(new Book("https://ddntcthcd.com/nettruyen/thumb/cao-vo-ha-canh-den-mot-van-nam-sau.jpg", "Boku no Pico", "Chapter 1"));
        bookList.add(new Book("https://ddntcthcd.com/nettruyen/thumb/vo-dich-don-ngo.jpg", "Two black man kissing", "Chapter 2"));
        bookList.add(new Book("https://kcgsbok.com/nettruyen/thumb/ta-co-mot-son-trai.jpg", "Why are you gay", "Chapter 3"));

        // Create an adapter and set it to the GridView
        bookAdapter = new BookAdapter(getActivity(), R.layout.item_book, bookList);
        newStoriesGridView.setAdapter(bookAdapter);

        return view;
    }
}
