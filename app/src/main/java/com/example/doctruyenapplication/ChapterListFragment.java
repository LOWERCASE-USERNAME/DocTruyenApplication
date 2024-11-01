package com.example.doctruyenapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctruyenapplication.adapter.BookAdapter;
import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.Chapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterListFragment extends Fragment {
    private ApiService apiService;
    private RecyclerView chapterRecyclerView;
    private ChapterAdapter chapterAdapter;
    private List<Chapter> chapters;
    public ChapterListFragment(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        chapterRecyclerView = view.findViewById(R.id.chapter_recycler_view);
        // Set up the RecyclerView for the chapters
        chapterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chapterAdapter = new ChapterAdapter(chapters, this::fetchChapter);
        chapterRecyclerView.setAdapter(chapterAdapter);

        apiService = RetrofitClient.getInstance().create(ApiService.class);

        return view;
    }

    private void openChapter(Chapter chapter) {
        ChapterFragment chapterFragment = new ChapterFragment();

        // Pass the chapter data to the ChapterFragment
        Bundle bundle = new Bundle();
        bundle.putString("chapter_title", "Chapter " + chapter.getChapterOrder() + ": " + chapter.getChapterName());
        bundle.putString("chapter_content", chapter.getContent());
        bundle.putInt("chapter_next", chapter.getNextChapterId());
        bundle.putInt("chapter_prev", chapter.getPrevChapterId());
        chapterFragment.setArguments(bundle);

        if (getActivity() instanceof BookDetailActivity) {
            ((BookDetailActivity) getActivity()).replaceWithChapterFragment(chapterFragment);
        }
    }

    private void fetchChapter(Chapter chapter){
        Call<Chapter> call = apiService.getChapterById(chapter.getChapterId());
        call.enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Chapter resultChapter = response.body();
                    openChapter(resultChapter);
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
}