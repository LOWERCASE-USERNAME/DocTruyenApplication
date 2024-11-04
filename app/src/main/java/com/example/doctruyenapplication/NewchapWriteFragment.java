package com.example.doctruyenapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class NewchapWriteFragment extends Fragment {

    private ImageView selectedImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_newchap, container, false);

        ImageButton backButton = view.findViewById(R.id.back_button);
        EditText chapNameEditText = view.findViewById(R.id.edt_newchap_name);
        EditText chapEditText = view.findViewById(R.id.edt_newchap);
        Button createButton = view.findViewById(R.id.create_chap_btn);
        // Đặt hành động cho nút quay lại
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        createButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
        return view;
    }
}