package com.example.doctruyenapplication;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoreStoriesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Nạp bố cục cho fragment này (layout cho xem thêm truyện)
        return inflater.inflate(R.layout.fragment_morestories, container, false);
    }
}
