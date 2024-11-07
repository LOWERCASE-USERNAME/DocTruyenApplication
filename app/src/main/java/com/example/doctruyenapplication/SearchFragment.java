package com.example.doctruyenapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import com.example.doctruyenapplication.adapter.BookHoriAdapter;
import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private GridView gridView;
    private BookHoriAdapter bookAdapter;
    private List<Book> bookList;
    private List<Book> filteredList; // List for filtered results
    private ApiService apiService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        gridView = view.findViewById(R.id.search_result);
        SearchView searchView = view.findViewById(R.id.search_view);

        apiService = RetrofitClient.getInstance().create(ApiService.class);
        loadBooksFromApi();

        // Set up the search functionality
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterBooks(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterBooks(newText);
                return false;
            }
        });

        // Set up item click listener for GridView
        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            Book selectedBook = filteredList.get(position);
            navigateToBookDetail(selectedBook);
        });

        return view;
    }

    private void loadBooksFromApi() {
        Call<List<Book>> call = apiService.getAllBooks();
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookList = response.body();
                    filteredList = new ArrayList<>(bookList); // Initialize filteredList
                    bookAdapter = new BookHoriAdapter(getContext(), R.layout.item_book_hori, filteredList);
                    gridView.setAdapter(bookAdapter);
                } else {
                    Log.e("API Error", "Response error: " + response.code());
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("API Error", "Network error: " + t.getMessage());
                Toast.makeText(getContext(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterBooks(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(bookList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (Book book : bookList) {
                if (book.getBookName().toLowerCase().contains(lowerCaseQuery)) {
                    filteredList.add(book);
                }
            }
        }

        bookAdapter = new BookHoriAdapter(getContext(), R.layout.item_book_hori, filteredList);
        gridView.setAdapter(bookAdapter);
//        bookAdapter.notifyDataSetChanged();
    }

    private void navigateToBookDetail(Book book) {
        Intent intent = new Intent(requireContext(), BookDetailActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }
}