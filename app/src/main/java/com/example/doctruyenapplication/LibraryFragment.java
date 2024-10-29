package com.example.doctruyenapplication;

import android.os.Bundle;
import android.util.Log;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment {
    private static final Map<Integer, String> STORY_TYPE_MAP = new HashMap<>();
    static {
        STORY_TYPE_MAP.put(R.id.story_type_1, "TU TIÊN");
        STORY_TYPE_MAP.put(R.id.story_type_2, "HUYỀN HUYỄN");
        STORY_TYPE_MAP.put(R.id.story_type_3, "CHUYỂN SINH");
        STORY_TYPE_MAP.put(R.id.story_type_4, "HÀI HƯỚC");
        STORY_TYPE_MAP.put(R.id.story_type_5, "NGÔN TÌNH");
        STORY_TYPE_MAP.put(R.id.story_type_6, "KINH DỊ");
    }

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

        // Khởi tạo danh sách sách
        bookList = new ArrayList<>();
        bookList.add(new Book("https://cdn.myanimelist.net/images/anime/12/39497.jpg", "Boku no Pico", "Chapter 1"));
        bookList.add(new Book("https://a.pinatafarm.com/500x377/d972ce254e/2-gay-black-mens-kissing.jpg", "Two black man kissing", "Chapter 2"));
        bookList.add(new Book("https://ih1.redbubble.net/image.4595760308.2867/flat,750x,075,f-pad,750x1000,f8f8f8.jpg", "Why are you gay", "Chapter 3"));

        // Gán adapter cho GridView
        bookAdapter = new BookAdapter(getActivity(), R.layout.item_book, bookList);
        newStoriesGridView.setAdapter(bookAdapter);
        bestStoriesGridView.setAdapter(bookAdapter);
        anStoriesGridView.setAdapter(bookAdapter);

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
        genres.forEach(g -> popupMenu.getMenu().add(g.getGenreName()));
//        popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
        popupMenu.show();
    }

    private boolean onMenuItemClick(MenuItem menuItem) {
//        String storyType = STORY_TYPE_MAP.get(menuItem.getItemId());
        String storyType = menuItem.getTitle().toString();
        if (storyType != null) {
            navigateToListStoriesFragment(storyType);
            return true;
        }
        return false;
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