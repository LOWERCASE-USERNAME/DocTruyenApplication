package com.example.doctruyenapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.Chapter;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewchapWriteFragment extends Fragment {
    private final ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
    private ImageView selectedImageView;
    EditText chapNameEditText, chapContentEditText, chapIdEditText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_newchap, container, false);

        ImageButton backButton = view.findViewById(R.id.back_button);
        chapIdEditText = view.findViewById(R.id.edt_newchap_id);
        chapNameEditText = view.findViewById(R.id.edt_newchap_name);
        chapContentEditText = view.findViewById(R.id.edt_newchap_content);
        Button createButton = view.findViewById(R.id.create_chap_btn);
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
        createButton.setOnClickListener(v -> createChapter());
        return view;
    }

    private void createChapter(){
        Chapter chapter = new Chapter();
        chapter.setBookId(17);
        chapter.setChapterName(chapNameEditText.getText().toString());
        chapter.setChapterOrder(Integer.parseInt(chapIdEditText.getText().toString()));
        chapter.setContent(chapContentEditText.getText().toString());
        postChapter(chapter);
    }

    private void postChapter(Chapter chapter){
        Call<Chapter> call = apiService.postChapter(chapter);
        call.enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    navigateToNewchapWriteFragment();
                    getActivity().getSupportFragmentManager().popBackStack();
                    Toast.makeText(requireContext(), "Thêm chapter " + chapter.getChapterName() + " thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Chapter> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage(), t);
            }
        });
    }
}