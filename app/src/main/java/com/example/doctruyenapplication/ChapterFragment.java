package com.example.doctruyenapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Chapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterFragment extends Fragment {

    private TextView chapterTitle;
    private TextView chapterContent;
    private Button nextChapterButton;
    private Button previousChapterButton;
    private Button commentButton;
    private ScrollView scrollView;

    private int currentChapterIndex = 0;  // Start at the first chapter (index 0)
    private List<Chapter> chapters;  // Hold the list of chapters
    private int nextChapterId = -1;
    private int prevChapterId = -1;
    private ApiService apiService;
    private int bookId,chapterId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter, container, false);
        apiService = RetrofitClient.getInstance().create(ApiService.class);

        // Initialize UI components
        chapterTitle = view.findViewById(R.id.chapter_title);
        chapterContent = view.findViewById(R.id.chapter_content);
        nextChapterButton = view.findViewById(R.id.next_chapter_button);
        previousChapterButton = view.findViewById(R.id.previous_chapter_button);
        commentButton = view.findViewById(R.id.comment_button);
        scrollView = view.findViewById(R.id.scroll_view);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);

        int accountId = sharedPreferences.getInt("accountId", 0);

        // Get arguments passed from the previous fragment
        Bundle bundle = getArguments();
        if (bundle != null) {
            chapterId = bundle.getInt("chapterId");
            bookId = bundle.getInt("bookId");
            String title = bundle.getString("chapter_title");
            String content = bundle.getString("chapter_content");
            nextChapterId = bundle.getInt("chapter_next");
            prevChapterId = bundle.getInt("chapter_prev");
            // Set the initial chapter title and content based on the passed data
            chapterTitle.setText(title);
            chapterContent.setText(content);
        } else {
            Toast.makeText(requireContext(), "Chapter argument not found", Toast.LENGTH_SHORT).show();
        }

        updateReadHistory(accountId,bookId,chapterId);

        nextChapterButton.setOnClickListener(v -> {
            if(nextChapterId != -1)
                fetchChapter(nextChapterId);

        });

        previousChapterButton.setOnClickListener(v -> {
            if(prevChapterId != -1)
                fetchChapter(prevChapterId);
        });

        commentButton.setOnClickListener(v -> openCommentFragment());
        return view;
    }

    // Method to load a new chapter from ChapterData
    private void loadChapter(Chapter chapter) {
        chapterTitle.setText("Chapter " + chapter.getChapterOrder() + ": " + chapter.getChapterName());
        chapterContent.setText(chapter.getContent());
        nextChapterId = chapter.getNextChapterId();
        prevChapterId = chapter.getPrevChapterId();
        // Scroll back to the top of the scroll view when the new chapter is loaded
        scrollView.scrollTo(0, 0);
    }

    private void fetchChapter(int chapterId){
        Call<Chapter> call = apiService.getChapterById(chapterId);

        call.enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Chapter resultChapter = response.body();
                    loadChapter(resultChapter);

                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Chapter> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage(), t);
            }
        });
    }
    private void updateReadHistory(int accountId,int bookId,int chapterId){
        Call<Void> call = apiService.updateReadHistory(accountId,bookId,chapterId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Chapter ID and Book ID have been saved successfully on the server
                    Log.d("Retrofit", "Read history updated successfully.");
                    // Optionally, you can call loadChapter or any additional logic here
                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage(), t);
            }
        });
    }


    // Method to open the CommentFragment
    private void openCommentFragment() {
        CommentFragment commentFragment = new CommentFragment(bookId);

        // Pass the current chapter's title to the CommentFragment
//        Bundle bundle = new Bundle();
//        bundle.putString("chapter_title", chapterTitle.getText().toString());
//        commentFragment.setArguments(bundle);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.book_detail_container, commentFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
