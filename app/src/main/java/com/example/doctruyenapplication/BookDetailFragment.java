package com.example.doctruyenapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.doctruyenapplication.object.Book;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BookDetailFragment extends Fragment {
    public static final String ARG_BOOK = "BOOK";
    private Book book;

    private TextView titleTextView;
    private TextView authorTextView;
    private TextView descriptionTextView;
    private TextView genreTextView;
    private RatingBar ratingBar;
    private TextView chaptersTextView;
    private TextView lastUpdateTextView;
    private ImageButton backButton;
    private ImageButton favoriteButton;
    private Button downloadButton;
    private Button readNowButton;
    private Button followButton;
    private Button storyInfoButton;

    public static BookDetailFragment newInstance(Book book) {
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

        titleTextView = view.findViewById(R.id.book_title);
        authorTextView = view.findViewById(R.id.book_author);
//        descriptionTextView = view.findViewById(R.id.book_description);
        genreTextView = view.findViewById(R.id.book_genre);
        ratingBar = view.findViewById(R.id.book_rating);
        chaptersTextView = view.findViewById(R.id.book_chapters);
        lastUpdateTextView = view.findViewById(R.id.last_update);
//        backButton = view.findViewById(R.id.back_button);
//        favoriteButton = view.findViewById(R.id.favorite_button);
//        downloadButton = view.findViewById(R.id.download_button);
//        readNowButton = view.findViewById(R.id.read_now_button);
//        followButton = view.findViewById(R.id.follow_button);
//        storyInfoButton = view.findViewById(R.id.chapter_list_button);

        Bundle arguments = getArguments();
        if (arguments != null) {
            book = (Book)arguments.getSerializable(ARG_BOOK);

            if(book != null){
                titleTextView.setText(book.getBookName());
                authorTextView.setText(book.getAuthors().get(0).getAuthorName());
//                descriptionTextView.setText(book.getDescription());
                genreTextView.setText(book.getGenres().get(0).getGenreName());
                chaptersTextView.setText(book.getChapters().get(book.getChapters().size() - 1).getChapterName());
            }else{
                Toast.makeText(requireContext(), "Argument book is null or unserializable", Toast.LENGTH_SHORT).show();
            }
        }

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        BookDetailTabAdapter adapter = new BookDetailTabAdapter(this, book);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Giới thiệu");
                        break;
                    case 1:
                        tab.setText("Đánh giá");
                        break;
                    case 2:
                        tab.setText("Bình luận");
                        break;
                    case 3:
                        tab.setText("D.S Chương");
                        break;
                }
            }
        }).attach();

//        storyInfoButton.setOnClickListener(v -> {
//            getParentFragmentManager().beginTransaction()
//                    .replace(R.id.container, new StoryInfoFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });
//        backButton.setOnClickListener(v -> getActivity().onBackPressed());
//        favoriteButton.setOnClickListener(v -> {
//            // Handle favorite action
//        });
//        downloadButton.setOnClickListener(v -> {
//            // Handle download action
//        });
//        readNowButton.setOnClickListener(v -> openChapterFragment());
//
//        followButton.setOnClickListener(v -> {
//            // Handle follow action
//        });

        return view;
    }

    private void openChapterFragment() {
        ChapterFragment chapterFragment = new ChapterFragment();
        // Pass any necessary data using a bundle
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, chapterFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}