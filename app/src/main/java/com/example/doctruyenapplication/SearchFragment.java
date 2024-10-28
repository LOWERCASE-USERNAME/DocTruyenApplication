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

import com.example.doctruyenapplication.adapter.BookHoriAdapter;
import com.example.doctruyenapplication.object.Book;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchInput;
    private ImageButton searchButton;
    private Button buttonStory, buttonAuthor;
    private GridView gridView;

    private boolean isSearchingForStories = true; // Mặc định tìm kiếm truyện
    private BookHoriAdapter adapter;
    private List<Book> bookList; // Danh sách sách

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

        // Tạo danh sách sách mẫu
        createBookList();

        // Khởi tạo adapter và thiết lập cho GridView
        adapter = new BookHoriAdapter(getContext(), R.layout.item_book_hori, bookList);
        gridView.setAdapter(adapter);

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
        // Thêm các cuốn sách mẫu
        bookList.add(new Book("https://cdn.myanimelist.net/images/anime/12/39497.jpg", "Boku no Pico", "Chapter 1"));
        bookList.add(new Book("https://a.pinatafarm.com/500x377/d972ce254e/2-gay-black-mens-kissing.jpg", "Two black men kissing", "Chapter 2"));
        bookList.add(new Book("https://ih1.redbubble.net/image.4595760308.2867/flat,750x,075,f-pad,750x1000,f8f8f8.jpg", "Why are you gay?", "Chapter 3"));
        bookList.add(new Book("https://dientusangtaovn.com/wp-content/uploads/2023/03/an-ba-to-com.jpg", "An ba to com", "Chapter 69"));
        bookList.add(new Book("https://oladino.com/wp-content/uploads/2024/09/diddy-be-out-here-slicker-than-ever-funny-diddy-baby-oil-meme-png-280924013-800x800.jpg", "How to yummy in Diddy's party", "Chapter 500"));
        // Thêm nhiều sách hơn nếu cần
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
        // Logic tải danh sách truyện
        adapter.updateData(bookList); // Hiển thị danh sách sách hiện tại
    }

    private void loadAuthors() {
        // Logic tải danh sách tác giả
        // Có thể sử dụng danh sách sách nếu bạn chỉ muốn hiển thị cùng một danh sách
        adapter.updateData(bookList); // Cập nhật nếu bạn có danh sách tác giả riêng
    }

    private void searchStories(String query) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getBookname().toLowerCase().contains(query.toLowerCase())) {
                filteredBooks.add(book);
            }
        }
        adapter.updateData(filteredBooks); // Cập nhật GridView với kết quả tìm kiếm
    }

    private void searchAuthors(String query) {
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : bookList) {
            if (book.getBookname().toLowerCase().contains(query.toLowerCase())) {
                filteredBooks.add(book);
            }
        }
        adapter.updateData(filteredBooks); // Cập nhật GridView với kết quả tìm kiếm
    }
}