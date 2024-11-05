package com.example.doctruyenapplication.api;

import com.example.doctruyenapplication.object.Account;
import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.Chapter;
import com.example.doctruyenapplication.object.Genre;
import com.example.doctruyenapplication.object.User;
import com.example.doctruyenapplication.object.BookReadHistory;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("genres")
    Call<List<Genre>> getGenres();
    @GET("books/all")
    Call<List<Book>> getAllBooks();
    @GET("books/get-by-writer-account-id/{accountId}")
    Call<List<Book>> getBooksByWriterId(@Path("accountId") int accountId);
    @GET("books")
    Call<List<Book>> getBooks(@Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize);
    @GET("books")
    Call<List<Book>> getBooks(@Query("pageNumber") int pageNumber, @Query("pageSize") int pageSize, @Query("genre") String genre);
    @GET("books/{id}")
    Call<Book> getBook(@Path("id") int id);
    @GET("chapters/{id}")
    Call<Chapter> getChapterById(@Path("id") int id);
    @GET("accounts/login")
    Call<Account>login(@Query("email") String email, @Query("password") String password);
    @POST("accounts/signup")
    Call<Account>signup(@Body Account account);
    @GET("users/get-by-account-id/{accountId}")
    Call<User> getUserByAccountId(@Path("accountId") int accountId);

    @GET("readHistory/getBookChapter/{accountId}")
    Call<List<BookReadHistory>> getReadBooks(@Path("accountId") int accountId);
    @PUT("readHistory/update/{accountId}/{bookId}/{chapterId}")
    Call<Void> updateReadHistory(
            @Path("accountId") int accountId,
            @Path("bookId") int bookId,
            @Path("chapterId") int chapterId
    );
    @POST("books")
    Call<Book> postBook(@Body Book book);
    @POST("chapters")
    Call<Chapter> postChapter(@Body Chapter chapter);
    @PUT("chapters")
    Call<Chapter> putChapter(@Body Chapter chapter);
    @DELETE("chapters/{id}")
    Call<Void> deleteChapter(@Path("id") int id);
}
