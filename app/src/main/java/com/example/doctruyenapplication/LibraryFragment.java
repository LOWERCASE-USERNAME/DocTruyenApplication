package com.example.doctruyenapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.doctruyenapplication.adapter.BannerAdapter;
import com.example.doctruyenapplication.adapter.BookAdapter;
import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.Genre;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {
    private GridView newStoriesGridView, bestStoriesGridView, anStoriesGridView;
    private ArrayList<Book> bookList;
    private BookAdapter bookAdapter;
    private ImageButton menuButton;
    private ApiService apiService;
    private List<Genre> genres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        apiService = RetrofitClient.getInstance().create(ApiService.class);
        fetchGenres();

        // Khởi tạo GridView
        newStoriesGridView = view.findViewById(R.id.new_stories_grid);
        bestStoriesGridView = view.findViewById(R.id.best_stories_grid);
        anStoriesGridView = view.findViewById(R.id.an_stories_grid);
        setupGridViewItemClick(new GridView[]{newStoriesGridView, bestStoriesGridView, anStoriesGridView});

        // Khởi tạo danh sách sách
        fetchBooks();

        // Khởi tạo ViewPager2 cho banner
        ViewPager2 viewPager = view.findViewById(R.id.banner_viewpager);
        int[] images = {
                R.drawable.tptk,
                R.drawable.dldl,
                R.drawable.dptk
        };
        BannerAdapter bannerAdapter = new BannerAdapter(getActivity(), images);
        viewPager.setAdapter(bannerAdapter);

        // Thiết lập auto scroll cho ViewPager2
        setupAutoScroll(viewPager);

        // Khởi tạo nút menu
        menuButton = view.findViewById(R.id.menu_button);
        menuButton.setOnClickListener(v -> showMenu());

        // Thiết lập click listener
        setupClickListeners(view);

        return view;
    }

    private void fetchGenres(){
        Call<List<Genre>> call = apiService.getGenres();
        call.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    genres = response.body();
                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
            }
        });
    }

    private void fetchBooks(){
        Call<List<Book>> call = apiService.getBooks(0, 3);
        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    bookList = (ArrayList<Book>) response.body();

                    // Gán adapter cho GridView
                    bookAdapter = new BookAdapter(getActivity(), R.layout.item_book, bookList);
                    newStoriesGridView.setAdapter(bookAdapter);
                    bestStoriesGridView.setAdapter(bookAdapter);
                    anStoriesGridView.setAdapter(bookAdapter);
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

    private void setupAutoScroll(ViewPager2 viewPager) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (viewPager.getCurrentItem() == 2) {
                    viewPager.setCurrentItem(0);
                } else {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
                handler.postDelayed(this, 15000);
            }
        };
        handler.postDelayed(runnable, 5000);
    }

    private void setupClickListeners(View view) {
        ImageButton searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> navigateToFragment(new SearchFragment()));

        TextView viewMoreNewStories = view.findViewById(R.id.view_more_button);
        viewMoreNewStories.setOnClickListener(v -> navigateToFragment(new MoreStoriesFragment()));

        TextView viewMoreBestStories = view.findViewById(R.id.view_more_button1);
        viewMoreBestStories.setOnClickListener(v -> navigateToFragment(new MoreStoriesFragment()));

        TextView viewMoreAwardStories = view.findViewById(R.id.view_more_button2);
        viewMoreAwardStories.setOnClickListener(v -> navigateToFragment(new MoreStoriesFragment()));
    }

    private void showMenu() {
        PopupMenu popupMenu = new PopupMenu(requireContext(), menuButton);
        if(genres == null){
            Toast.makeText(requireContext(), "API not connected", Toast.LENGTH_SHORT).show();
        }else{
            genres.forEach(g -> popupMenu.getMenu().add(g.getGenreName()));
        }
//        popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
        popupMenu.show();
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
        String storyType = menuItem.getTitle().toString();
        if (storyType != null) {
            navigateToListStoriesFragment(storyType);
            return true;
        }
        return false;
    }

    // Set up item click listeners for each GridView to navigate to the chapter details
    private void setupGridViewItemClick(GridView[] gridViews) {
        for(GridView gridView : gridViews){
            gridView.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
                Book selectedBook = bookList.get(position);
                navigateToChapterDetailFragment(selectedBook);
            });
        }
    }

    // Navigate to the ChapterDetailFragment with the selected book
    private void navigateToChapterDetailFragment(Book book) {
//        BookDetailFragment bookDetailFragment = BookDetailFragment.newInstance(book);
        Intent intent = new Intent(requireContext(), BookDetailActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
//        navigateToFragment(bookDetailFragment);
    }

    private void navigateToListStoriesFragment(String storyType) {
        ListStory_Fragment fragment = ListStory_Fragment.newInstance(storyType);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}