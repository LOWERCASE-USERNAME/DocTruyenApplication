package com.example.doctruyenapplication.api;

import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.Genre;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("genres")
    Call<List<Genre>> getGenres();
    @GET("books/all")
    Call<List<Book>> getAllBooks();
    @GET("books")
    Call<List<Book>> getBooks(@Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);
    @GET("books")
    Call<List<Book>> getBooks(@Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize, @Query("genre") String genre);
}
