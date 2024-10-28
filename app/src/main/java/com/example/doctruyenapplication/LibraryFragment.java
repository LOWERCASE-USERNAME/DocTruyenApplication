package com.example.doctruyenapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class LibraryFragment extends Fragment {

    private ImageButton searchButton;
    private TextView viewMoreButton;
    private TextView infoButton1, infoButton2, infoButton3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        List<ChapterData.Chapter> chapters = ChapterData.getChapters();

        infoButton1 = view.findViewById(R.id.chapter_button_1);
        infoButton1.setText(chapters.get(0).getTitle());
        infoButton1.setOnClickListener(v -> openChapterDetailFragment(chapters.get(0)));

        infoButton2 = view.findViewById(R.id.chapter_button_2);
        infoButton2.setText(chapters.get(1).getTitle());
        infoButton2.setOnClickListener(v -> openChapterDetailFragment(chapters.get(1)));

        infoButton3 = view.findViewById(R.id.chapter_button_3);
        infoButton3.setText(chapters.get(2).getTitle());
        infoButton3.setOnClickListener(v -> openChapterDetailFragment(chapters.get(2)));

        searchButton = view.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> openFragment(new SearchFragment()));

        viewMoreButton = view.findViewById(R.id.view_more_button);
        viewMoreButton.setOnClickListener(v -> openFragment(new MoreStoriesFragment()));

        return view;
    }

    private void openChapterDetailFragment(ChapterData.Chapter chapter) {
        Bundle bundle = new Bundle();
        bundle.putString("chapter_title", chapter.getTitle());
        bundle.putString("chapter_author", chapter.getAuthor()); // Assuming there is an author field
        bundle.putString("chapter_description", chapter.getContent());

        ChapterDetailFragment chapterDetailFragment = new ChapterDetailFragment();
        chapterDetailFragment.setArguments(bundle);

        openFragment(chapterDetailFragment);
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}