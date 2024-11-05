package com.example.doctruyenapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyenapplication.object.Chapter;

import org.w3c.dom.Text;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder> {

    private List<Chapter> chapterList;
    private OnChapterClickListener onChapterClickListener;

    public ChapterAdapter(List<Chapter> chapterList, OnChapterClickListener listener) {
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
        Chapter chapter = chapterList.get(position);
        if(chapter.getChapterId() == -1){
            holder.chapterButton.setBackgroundColor(Color.LTGRAY);
            holder.chapterButton.setText("+ " + chapter.getChapterName());
            holder.chapterButton.setOnClickListener(v -> onChapterClickListener.onChapterClick(chapter));
            return;
        }
        holder.chapterButton.setText("Chapter " + chapter.getChapterOrder() + ": " + chapter.getChapterName());

        holder.chapterButton.setOnClickListener(v -> onChapterClickListener.onChapterClick(chapter));
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public static class ChapterViewHolder extends RecyclerView.ViewHolder {
        TextView chapterButton;

        public ChapterViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterButton = itemView.findViewById(R.id.chapter_button);
        }
    }

    // Interface for handling chapter button clicks
    public interface OnChapterClickListener {
        void onChapterClick(Chapter chapter);
    }
}
