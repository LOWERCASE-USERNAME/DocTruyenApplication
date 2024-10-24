package com.example.doctruyenapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private List<ChapterData.Chapter> chapterList;
    private OnChapterClickListener onChapterClickListener;

    public ChapterAdapter(List<ChapterData.Chapter> chapterList, OnChapterClickListener listener) {
        this.chapterList = chapterList;
        this.onChapterClickListener = listener;
    }

    @NonNull
    @Override
    public ChapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter_button, parent, false);
        return new ChapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterViewHolder holder, int position) {
        ChapterData.Chapter chapter = chapterList.get(position);
        holder.chapterButton.setText(chapter.getTitle());

        holder.chapterButton.setOnClickListener(v -> onChapterClickListener.onChapterClick(chapter));
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {
        Button chapterButton;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterButton = itemView.findViewById(R.id.chapter_button);
        }
    }

    // Interface for handling chapter button clicks
    public interface OnChapterClickListener {
        void onChapterClick(ChapterData.Chapter chapter);
    }
}
