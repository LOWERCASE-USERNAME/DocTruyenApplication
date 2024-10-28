package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StoryInfoFragment extends Fragment {

    private ImageView storyCoverImage;
    private TextView authorName;
    private RecyclerView chapterRecyclerView;
    private ChapterAdapter chapterAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_info, container, false);

        // Initialize views
        storyCoverImage = view.findViewById(R.id.story_cover_image);
        authorName = view.findViewById(R.id.author_name);
        chapterRecyclerView = view.findViewById(R.id.chapter_recycler_view);

        // Set dummy data (You can replace this with real data later)
        storyCoverImage.setImageResource(R.drawable.tptk2);  // Set the story cover image
        authorName.setText("Nguyễn Nhật Ánh");  // Set the author name

        // Load chapter data
        List<ChapterData.Chapter> chapters = ChapterData.getChapters();

        // Set up the RecyclerView for the chapters
        chapterRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chapterAdapter = new ChapterAdapter(chapters, this::openChapter);
        chapterRecyclerView.setAdapter(chapterAdapter);

        return view;
    }

    // Method to open a chapter when a button is clicked
    private void openChapter(ChapterData.Chapter chapter) {
        ChapterFragment chapterFragment = new ChapterFragment();

        // Pass the chapter data to the ChapterFragment
        Bundle bundle = new Bundle();
        bundle.putString("chapter_title", chapter.getTitle());
        bundle.putString("chapter_content", chapter.getContent());
        chapterFragment.setArguments(bundle);

        // Open the ChapterFragment
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container, chapterFragment)
                .addToBackStack(null)
                .commit();
    }
}
