package com.example.doctruyenapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class AccountFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        LinearLayout optionRegisterToWrite = view.findViewById(R.id.option_register_to_write);
        optionRegisterToWrite.setOnClickListener(v -> navigateToFragment(new AccountFragment()));

        LinearLayout optionWriteBook = view.findViewById(R.id.option_write_book);
        optionWriteBook.setOnClickListener(v -> navigateToFragment(new AccountFragment()));

        LinearLayout optionAboutUs = view.findViewById(R.id.option_about_us);
        optionAboutUs.setOnClickListener(v -> navigateToFragment(new AboutUsFragment()));

        LinearLayout optionPolicy = view.findViewById(R.id.option_policy);
        // Added code to navigate to the PolicyFragment
        optionPolicy.setOnClickListener(v -> navigateToFragment(new PolicyFragment()));

        return view;
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
