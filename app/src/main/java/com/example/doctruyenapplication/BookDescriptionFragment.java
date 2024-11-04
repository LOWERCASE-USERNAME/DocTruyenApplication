package com.example.doctruyenapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookDescriptionFragment extends Fragment {
    private String description;

    public BookDescriptionFragment(String description) {
        this.description = description;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_description, container, false);
        ((TextView)view.findViewById(R.id.tvBookDescription)).setText(description);
        return view;
    }
}