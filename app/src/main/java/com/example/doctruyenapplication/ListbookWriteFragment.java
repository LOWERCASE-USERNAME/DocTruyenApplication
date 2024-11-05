package com.example.doctruyenapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

import com.example.doctruyenapplication.adapter.BookAdapter;
import com.example.doctruyenapplication.adapter.BookHoriAdapter;
import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListbookWriteFragment extends Fragment {
    private final ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_author_listbook, container, false);
        GridView MyStoriesGrid = view.findViewById(R.id.list_stories_grid);
        fetchBooks(MyStoriesGrid);

        ImageButton backButton = view.findViewById(R.id.back_button);
        Button writeNewBookButton = view.findViewById(R.id.write_new_book);
        writeNewBookButton.setOnClickListener(v -> navigateToNewbookWriteFragment());
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void fetchBooks(GridView gridView){
        Call<List<Book>> call = apiService.getAllBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<Book> bookList = (ArrayList<Book>) response.body();

                    BookHoriAdapter bookAdapter = new BookHoriAdapter(requireContext(), R.layout.item_book_hori, bookList);
                    gridView.setAdapter(bookAdapter);
                    gridView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                        Book selectedBook = bookList.get(position);
                        navigateToNewchapWriteFragment(selectedBook);
//                        navigateToChapterDetailFragment(selectedBook);
//                        if (bookAdapter.isSelectionMode()) {
//                            boolean isSelected = !bookAdapter.getSelectedItems().get(position);
//                            bookAdapter.getSelectedItems().set(position, isSelected);
//                            bookAdapter.notifyDataSetChanged();
//                        } else {
//                            navigateToChapterDetailFragment(selectedBook);
//                        }
                    });
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

    private void navigateToNewbookWriteFragment() {
        Fragment newBookFragment = new NewbookWriteFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, newBookFragment); // Ensure 'container' ID matches your layout
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToNewchapWriteFragment(Book book) {
        Fragment newChapFragment = new NewchapWriteFragment();
        Bundle args = new Bundle();
        args.putSerializable("book", book);
        newChapFragment.setArguments(args);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, newChapFragment); // Ensure 'container' ID matches your layout
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToChapterDetailFragment(Book book) {
        Intent intent = new Intent(requireContext(), BookDetailActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }
}