package com.example.doctruyenapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doctruyenapplication.R;
import com.example.doctruyenapplication.object.Author;

import java.io.InputStream;
import java.util.List;

public class AuthorAdapter extends BaseAdapter {
    private Context context;
    private List<Author> authorList;

    public AuthorAdapter(Context context, List<Author> authorList) {
        this.context = context;
        this.authorList = authorList;
    }

    @Override
    public int getCount() {
        return authorList.size();
    }

    @Override
    public Object getItem(int position) {
        return authorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return authorList.get(position).getAuthorId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_author, parent, false);
        }

        Author author = authorList.get(position);

        ImageView authorImage = convertView.findViewById(R.id.author_image);
        TextView authorName = convertView.findViewById(R.id.author_name);

        authorName.setText(author.getAuthorName());
        new LoadImageTask(authorImage).execute(author.getAuthorImg());
        return convertView;
    }

    public void updateData(List<Author> newAuthors) {
        this.authorList = newAuthors;
        notifyDataSetChanged();
    }

    private static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageView;

        public LoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}