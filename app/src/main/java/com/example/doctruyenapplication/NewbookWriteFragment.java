package com.example.doctruyenapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.doctruyenapplication.api.ApiService;
import com.example.doctruyenapplication.api.RetrofitClient;
import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.Genre;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewbookWriteFragment extends Fragment {
    private final ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
    private List<Genre> genres;
    private ImageView selectedImageView;
    private Button genreButton;
    EditText bookNameEditText, edtImageUrl, edtDescription;
    ArrayList<Integer> selectedGenreIds;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_newbook, container, false);
        fetchGenres();
        // Khởi tạo các view
        edtImageUrl = view.findViewById(R.id.edt_image_url);
        edtDescription = view.findViewById(R.id.edt_newbook_des);
        selectedImageView = view.findViewById(R.id.selected_image_view);
        ImageButton backButton = view.findViewById(R.id.back_button);
        bookNameEditText = view.findViewById(R.id.edt_newbook_name);
        genreButton = view.findViewById(R.id.select_genres_button);
//        LinearLayout selectedGenresContainer = view.findViewById(R.id.selected_genres_container);
        Button createButton = view.findViewById(R.id.create_book_btn);
        backButton.setOnClickListener(v -> getActivity().getSupportFragmentManager().popBackStack());
        createButton.setOnClickListener(v -> createBook());

        edtImageUrl.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String imageUrl = edtImageUrl.getText().toString().trim();

                    if (!imageUrl.isEmpty()) {
                        Glide.with(getContext())
                                .load(imageUrl)  // Load the URL
                                .into(selectedImageView);  // Set the image in ImageView

                        selectedImageView.setVisibility(View.VISIBLE);
                    } else {
                        selectedImageView.setVisibility(View.GONE);
                    }
                }
            }
        });

        return view;
    }

    private void createBook(){
        Book book = new Book();
        genres.stream().filter(g -> selectedGenreIds.contains(g.getGenreId()));
        book.setGenres(genres);
        book.setBookName(bookNameEditText.getText().toString());
        book.setPictureLink(edtImageUrl.getText().toString());
        book.setDescription(edtDescription.getText().toString());
        book.setStatus(1);
        book.setUploadTime(new Date());
        postBook(book);
    }

    private void postBook(Book book){
        Call<Book> call = apiService.postBook(book);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful() && response.body() != null) {
//                    navigateToNewchapWriteFragment();
                    getActivity().getSupportFragmentManager().popBackStack();
                    Toast.makeText(requireContext(), "Thêm truyện " + book.getBookName() + " thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage(), t);
            }
        });
    }

    private void fetchGenres(){
        Call<List<Genre>> call = apiService.getGenres();
        call.enqueue(new Callback<List<Genre>>() {
            @Override
            public void onResponse(Call<List<Genre>> call, Response<List<Genre>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    genres = response.body();
                    genreButton.setOnClickListener(view -> SelectGenresClickHandler());
                } else {
                    Log.e("Retrofit", "Response error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Genre>> call, Throwable t) {
                Log.e("Retrofit", "Network error: " + t.getMessage());
            }
        });
    }

    private void SelectGenresClickHandler(){
        boolean[] checkedItems = getSavedCheckedState();
        String[] genreNames = new String[genres.size()];
        int[] genreIds = new int[genres.size()];

        for (int i = 0; i < genres.size(); i++) {
            genreNames[i] = genres.get(i).getGenreName();
            genreIds[i] = genres.get(i).getGenreId();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Select Genres")
                .setMultiChoiceItems(genreNames, checkedItems, (dialog, which, isChecked) -> {
                    // Handle item checks
                    checkedItems[which] = isChecked;
                })
                .setPositiveButton("OK", (dialog, which) -> {
                    selectedGenreIds = new ArrayList<>();
                    // Handle the selection and use the checkedItems array
                    for (int i = 0; i < checkedItems.length; i++) {
                        if (checkedItems[i]) {
                            // This genre is selected
                            selectedGenreIds.add(genreIds[i]);
                            Log.d("Selected Genre", genres.get(i).getGenreName());
                        }
                    }
                    saveCheckedState(checkedItems);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private boolean[] getSavedCheckedState() {
        SharedPreferences prefs = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        boolean[] checkedItems = new boolean[genres.size()];
        for (int i = 0; i < genres.size(); i++) {
            checkedItems[i] = prefs.getBoolean("genre_" + genres.get(i).getGenreId(), false);
        }
        return checkedItems;
    }

    // Method to save the checked state in SharedPreferences
    private void saveCheckedState(boolean[] checkedItems) {
        SharedPreferences prefs = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for (int i = 0; i < genres.size(); i++) {
            editor.putBoolean("genre_" + genres.get(i).getGenreId(), checkedItems[i]);
        }
        editor.apply();
    }

    private void navigateToNewchapWriteFragment() {
        Fragment newChapFragment = new NewchapWriteFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, newChapFragment); // Ensure 'container' ID matches your layout
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onPause() {
        super.onPause();
        // Clear the saved result when the fragment is no longer in use
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE).edit();
        for (Genre genre : genres) {
            editor.remove("genre_" + genre.getGenreId()); // Remove each genre's checked state
        }

        editor.apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE).edit();
        for (Genre genre : genres) {
            editor.remove("genre_" + genre.getGenreId()); // Remove each genre's checked state
        }

        editor.apply();
    }
}