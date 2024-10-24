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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class BookAdapter extends ArrayAdapter<Book> {
    private Context ct;
    private ArrayList<Book> arr;

    public BookAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }
    public void sortTruyen(String s){
        s= s.toUpperCase();
        int k=0;
        for(int i=0; i<arr.size();i++){
            Book t = arr.get(i);
            String ten = t.getBookname().toUpperCase();
            if(ten.indexOf(s)>=0){
                arr.set(i,arr.get(k));
                arr.set(k,t);
                k++;
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_book, null);
        }
        if (arr.size() > 0) {
            Book book = this.arr.get(position);
            TextView tentenTruyen = convertView.findViewById(R.id.txvTenTruyen);
            TextView tentenChap = convertView.findViewById(R.id.txvTenChap);
            ImageView anhTruyen = convertView.findViewById(R.id.txvImage);

            tentenTruyen.setText(book.getBookname());
            tentenChap.setText(book.getBookchap());
            Glide.with(this.ct).load(book.getLink()).into(anhTruyen);
        }
        return convertView;
    }


}