package com.example.doctruyenapplication.api;

import com.example.doctruyenapplication.object.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BookApiService {
    @GET("https://api.myjson.online/v1/records/9c696a8a-5c05-4a14-b3e5-92b854194fdc") // Replace with actual endpoint
    Call<List<Book>> getBooks();
}
