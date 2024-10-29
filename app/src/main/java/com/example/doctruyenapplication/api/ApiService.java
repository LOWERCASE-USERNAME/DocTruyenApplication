package com.example.doctruyenapplication.api;

import com.example.doctruyenapplication.object.Genre;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("genres")
    Call<List<Genre>> getGenres();
}
