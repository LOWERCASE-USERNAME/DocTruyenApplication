package com.example.doctruyenapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.doctruyenapplication.adapter.AuthorAdapter;
import com.example.doctruyenapplication.adapter.BookHoriAdapter;
import com.example.doctruyenapplication.object.Author;
import com.example.doctruyenapplication.object.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchInput;
    private ImageButton searchButton;
    private Button buttonStory, buttonAuthor;
    private GridView gridView;

    private boolean isSearchingForStories = true; // Mặc định tìm kiếm truyện
    private BookHoriAdapter bookAdapter;
    private AuthorAdapter authorAdapter;
    private List<Book> bookList; // Danh sách tất cả sách
    private List<Author> authorList; // Danh sách tác giả

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Nạp bố cục cho fragment này
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Khởi tạo các thành phần UI
        searchInput = view.findViewById(R.id.search_input);
        searchButton = view.findViewById(R.id.search_button);
        buttonStory = view.findViewById(R.id.button_story);
        buttonAuthor = view.findViewById(R.id.button_author);
        gridView = view.findViewById(R.id.grid_view);

        // Tạo danh sách sách và tác giả mẫu
        createBookList();
        createAuthorList();

        // Khởi tạo adapter cho sách
        bookAdapter = new BookHoriAdapter(getContext(), R.layout.item_book_hori, bookList);
        gridView.setAdapter(bookAdapter);

        // Thiết lập sự kiện cho nút tìm kiếm
        searchButton.setOnClickListener(v -> performSearch());

        // Thiết lập sự kiện cho nút truyện
        buttonStory.setOnClickListener(v -> {
            isSearchingForStories = true;
            loadStories();
        });

        // Thiết lập sự kiện cho nút tác giả
        buttonAuthor.setOnClickListener(v -> {
            isSearchingForStories = false;
            loadAuthors();
        });

        // Tải danh sách truyện mặc định
        loadStories();

        return view;
    }

    private void createBookList() {
        bookList = new ArrayList<>();
    }

    private void createAuthorList() {
        authorList = new ArrayList<>();
    }

    private void performSearch() {
        String query = searchInput.getText().toString().trim();
        if (query.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
            return;
        }

        // Logic tìm kiếm truyện hoặc tác giả dựa trên isSearchingForStories
        if (isSearchingForStories) {
            searchStories(query);
        } else {
            searchAuthors(query);
        }
    }

    private void loadStories() {
        gridView.setAdapter(bookAdapter); // Hiển thị danh sách truyện
        bookAdapter.updateData(bookList); // Cập nhật dữ liệu cho adapter sách
    }

    private void loadAuthors() {
        if (authorAdapter == null) {
            authorAdapter = new AuthorAdapter(getContext(), authorList);
        }
        gridView.setAdapter(authorAdapter); // Hiển thị danh sách tác giả
        authorAdapter.updateData(authorList); // Cập nhật dữ liệu cho adapter tác giả
    }

    private void searchStories(String query) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : bookList) {
            // Kiểm tra xem tên sách có chứa từ khóa tìm kiếm không
            if (book.getBookName().toLowerCase().contains(query.toLowerCase())) {
                filteredBooks.add(book);
            }
        }
        bookAdapter.updateData(filteredBooks); // Cập nhật GridView với kết quả tìm kiếm
    }

    private void searchAuthors(String query) {
        List<Author> filteredAuthors = new ArrayList<>();
        for (Author author : authorList) {
            if (author.getAuthorName().toLowerCase().contains(query.toLowerCase())) {
                filteredAuthors.add(author);
            }
        }
        authorAdapter.updateData(filteredAuthors); // Cập nhật GridView với kết quả tìm kiếm
    }
}