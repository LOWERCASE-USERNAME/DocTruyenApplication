package com.example.doctruyenapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.doctruyenapplication.R;
import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.Chapter;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;
    private List<Book> bookList;

    public BookAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource, objects);
        this.context = context;
        this.bookList = new ArrayList<>(objects);
    }

    // Phương thức tìm kiếm sách
    public void filterBooks(String query) {
        String searchQuery = query.toUpperCase();
        List<Book> filteredList = new ArrayList<>();

        for (Book book : bookList) {
            String bookName = book.getBookName().toUpperCase();
            if (bookName.contains(searchQuery)) {
                filteredList.add(book);
            }
        }

        // Cập nhật danh sách và thông báo thay đổi
        clear();
        addAll(filteredList);
        notifyDataSetChanged();

        // Update the reference list
        bookList = filteredList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_book, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.txvTenTruyen);
            holder.chapterTextView = convertView.findViewById(R.id.txvTenChap);
            holder.imageView = convertView.findViewById(R.id.txvImage);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Kiểm tra vị trí có hợp lệ
        if (position >= 0 && position < bookList.size()) {
            Book book = bookList.get(position);
            holder.titleTextView.setText(book.getBookName());

            // Check for null chapters
            List<Chapter> chapters = book.getChapters();
            if (chapters != null && !chapters.isEmpty()) {
                Chapter newestChapter = chapters.get(chapters.size() - 1);
                holder.chapterTextView.setText(String.format("Chapter %d: %s", newestChapter.getChapterOrder(), newestChapter.getChapterName()));
            } else {
                holder.chapterTextView.setText("No chapters available");
            }

            // Load image with Glide and a placeholder
            Glide.with(context)
                    .load(book.getPictureLink())
                    .into(holder.imageView);

        }

        return convertView;
    }

    // Lớp ViewHolder để tối ưu hóa hiệu suất
    static class ViewHolder {
        TextView titleTextView;
        TextView chapterTextView;
        ImageView imageView;
    }
}
