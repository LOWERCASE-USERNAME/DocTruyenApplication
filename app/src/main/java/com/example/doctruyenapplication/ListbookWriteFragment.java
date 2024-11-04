package com.example.doctruyenapplication;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class ListbookWriteFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_author_listbook, container, false);
        ImageButton backButton = view.findViewById(R.id.back_button);
        Button writeNewBookButton = view.findViewById(R.id.write_new_book);
        writeNewBookButton.setOnClickListener(v -> navigateToNewbookWriteFragment());
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void navigateToNewbookWriteFragment() {
        Fragment newBookFragment = new NewbookWriteFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, newBookFragment); // Ensure 'container' ID matches your layout
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}