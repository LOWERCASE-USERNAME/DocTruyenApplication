package com.example.doctruyenapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AccountFragment extends  Fragment{
    Button btnLogin,btnLogout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Nạp bố cục cho fragment này
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        LinearLayout optionRegisterToWrite = view.findViewById(R.id.option_register_to_write);
        optionRegisterToWrite.setOnClickListener(v -> navigateToFragment(new AccountFragment()));

        LinearLayout optionWriteBook = view.findViewById(R.id.option_write_book);
        optionWriteBook.setOnClickListener(v -> navigateToFragment(new AccountFragment()));

        LinearLayout optionAboutUs = view.findViewById(R.id.option_about_us);
        optionAboutUs.setOnClickListener(v -> navigateToFragment(new AccountFragment()));

        LinearLayout optionPolicy = view.findViewById(R.id.option_policy);
        optionPolicy.setOnClickListener(v -> navigateToFragment(new AccountFragment()));

        btnLogin = view.findViewById(R.id.btn_login);
        btnLogout = view.findViewById(R.id.btn_logout);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        Log.e("tranhoanggiang",isLoggedIn+"");
        if (isLoggedIn) {
            btnLogout.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), Login.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), Login.class);
                startActivity(intent);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isLoggedIn", false); // Lưu cờ đăng nhập
                editor.apply();
            }
        });


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
