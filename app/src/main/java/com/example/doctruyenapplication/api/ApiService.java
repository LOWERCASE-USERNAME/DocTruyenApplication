package com.example.doctruyenapplication.api;

import com.example.doctruyenapplication.object.Account;
import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.Chapter;
import com.example.doctruyenapplication.object.Genre;
import com.example.doctruyenapplication.object.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
    @GET("chapters/{id}")
    Call<Chapter> getChapterById(@Path("id") int id);
    @GET("accounts/login")
    Call<Account>login(@Query("email") String email, @Query("password") String password);
    @POST("accounts/signup")
    Call<Account>signup(@Body Account account);
    @GET("users/get-by-account-id/{accountId}")
    Call<User> getUserByAccountId(@Path("accountId") int accountId);
    @POST("books")
    Call<Book> postBook(@Body Book book);
    @POST("chapters")
    Call<Chapter> postChapter(@Body Chapter chapter);
}
