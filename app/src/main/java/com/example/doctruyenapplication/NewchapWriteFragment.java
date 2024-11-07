package com.example.doctruyenapplication;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.Chapter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewchapWriteFragment extends Fragment {
    private final ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
    private ImageView selectedImageView;
    EditText chapNameEditText, chapContentEditText, chapIdEditText;
    private Book book;
    private Chapter selectedChapter;

    public NewchapWriteFragment(){}

    public NewchapWriteFragment(Book book){
        this.book = book;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_newchap, container, false);

        ImageButton backButton = view.findViewById(R.id.back_button);
        chapIdEditText = view.findViewById(R.id.edt_newchap_id);
        chapNameEditText = view.findViewById(R.id.edt_newchap_name);
        chapContentEditText = view.findViewById(R.id.edt_newchap_content);
        Button createButton = view.findViewById(R.id.create_chap_btn);
        Button updateButton = view.findViewById(R.id.update_chap_btn);
        Button deleteButton = view.findViewById(R.id.delete_chap_btn);
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
        createButton.setOnClickListener(v -> postChapter(createChapter()));
//        createButton.setEnabled(false);
        updateButton.setOnClickListener(v -> putChapter(createChapter()));
        deleteButton.setOnClickListener(v -> deleteChapter(createChapter()));
        Bundle arguments = getArguments();

        if (arguments != null) {
            book = (Book)arguments.getSerializable("book");

            if(book != null){
                chapIdEditText.setEnabled(false);
                if(!book.getChapters().isEmpty()){
                    int newestChapterOrder = book.getChapters().get(book.getChapters().size() - 1).getChapterOrder();
//                    chapIdEditText.setText(String.valueOf(newestChapterOrder + 1));
                    List<Chapter> chapters = book.getChapters();
                    Chapter newChapter = new Chapter();
                    newChapter.setChapterId(-1);
                    newChapter.setChapterOrder(newestChapterOrder + 1);
                    newChapter.setChapterName("Thêm chapter mới");
                    chapters.add(newChapter);
                    book.setChapters(chapters);
                }else{
                    chapIdEditText.setText(String.valueOf(1));
                    updateButton.setVisibility(View.GONE);
                    deleteButton.setVisibility(View.GONE);
                    LinearLayout.LayoutParams params =
                            (LinearLayout.LayoutParams) createButton.getLayoutParams();
                    params.gravity = Gravity.CENTER;
                    params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    createButton.setLayoutParams(params);
                }
            }else{
                Toast.makeText(requireContext(), "Argument book is null or unserializable", Toast.LENGTH_SHORT).show();
            }
        }

        ChapterListFragment chapterListFragment = new ChapterListFragment(book.getChapters(), chapter -> {
            if(chapter.getChapterId() == -1){
                selectedChapter = null;

                chapIdEditText.setEnabled(false);
                chapIdEditText.setText(String.valueOf(chapter.getChapterOrder()));
                chapNameEditText.setText("");
                chapContentEditText.setText("");
                createButton.setEnabled(true);
                return;
            }
            createButton.setEnabled(false);
            fetchChapter(chapter);

        });
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.chapter_list_container, chapterListFragment)
                .commit();
        return view;
    }

    private Chapter createChapter(){
        Chapter chapter = new Chapter();
        chapter.setChapterId(selectedChapter != null ? selectedChapter.getChapterId() : 0);
        chapter.setBookId(book.getBookId());
        chapter.setChapterName(chapNameEditText.getText().toString());
        chapter.setChapterOrder(Integer.parseInt(chapIdEditText.getText().toString()));
        chapter.setContent(chapContentEditText.getText().toString());
        return chapter;
    }

    private void postChapter(Chapter chapter){
        Call<Chapter> call = apiService.postChapter(chapter);
        call.enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    getActivity().getSupportFragmentManager().popBackStack();
                    Toast.makeText(requireContext(), "Thêm chapter " + chapter.getChapterName() + " thành công", Toast.LENGTH_SHORT).show();

                    List<Chapter> chapters = book.getChapters();

                    chapters.add(response.body());
                    book.setChapters(chapters);
                    reloadFragment();
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

    private void putChapter(Chapter chapter){
        Call<Chapter> call = apiService.putChapter(chapter);
        call.enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                if (response.isSuccessful()) {
//                    getActivity().getSupportFragmentManager().popBackStack();
                    Toast.makeText(requireContext(), "Sửa chapter " + chapter.getChapterName() + " thành công", Toast.LENGTH_SHORT).show();

                    List<Chapter> chapters = book.getChapters();
                    chapters.set(chapter.getChapterOrder() - 1, response.body());
                    book.setChapters(chapters);
                    reloadFragment();
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

    private void deleteChapter(Chapter chapter){
        Call<Void> call = apiService.deleteChapter(chapter.getChapterId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
//                    getActivity().getSupportFragmentManager().popBackStack();
                    Toast.makeText(requireContext(), "Xóa chapter " + chapter.getChapterName() + " thành công", Toast.LENGTH_SHORT).show();

                    List<Chapter> chapters = book.getChapters();
                    book.setChapters(chapters.stream().filter(c -> c.getChapterId() != chapter.getChapterId()).collect(Collectors.toList()));
                    reloadFragment();
                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage(), t);
            }
        });
    }

    private void fetchChapter(Chapter chapter){
        Call<Chapter> call = apiService.getChapterById(chapter.getChapterId());
        call.enqueue(new Callback<Chapter>() {
            @Override
            public void onResponse(Call<Chapter> call, Response<Chapter> response) {
                if (response.isSuccessful() && response.body() != null) {
                    selectedChapter = response.body();

                    chapIdEditText.setEnabled(false);
                    chapIdEditText.setText(String.valueOf(selectedChapter.getChapterOrder()));
                    chapNameEditText.setText(selectedChapter.getChapterName());
                    chapContentEditText.setText(selectedChapter.getContent());
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

    private void reloadFragment(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<Chapter> chapters = book.getChapters();
            book.setChapters(chapters.stream().filter(c -> c.getChapterId() != -1).collect(Collectors.toList()));
            getFragmentManager().beginTransaction().detach(this).commitNow();
            getFragmentManager().beginTransaction().attach(this).commitNow();
        } else {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}