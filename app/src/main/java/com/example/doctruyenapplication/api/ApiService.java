package com.example.doctruyenapplication.api;

import com.example.doctruyenapplication.Comment;
import com.example.doctruyenapplication.object.Account;
import com.example.doctruyenapplication.object.Book;
import com.example.doctruyenapplication.object.Chapter;
import com.example.doctruyenapplication.object.Genre;
import com.example.doctruyenapplication.object.User;
import com.example.doctruyenapplication.object.BookReadHistory;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET("readHistory/getBookChapter/{accountId}")
    Call<List<BookReadHistory>> getReadBooks(@Path("accountId") int accountId);
    @PUT("readHistory/update/{accountId}/{bookId}/{chapterId}")
    Call<Void> updateReadHistory(
            @Path("accountId") int accountId,
            @Path("bookId") int bookId,
            @Path("chapterId") int chapterId
    );

    @GET("comments/getComments/{bookId}")
    Call<List<Comment>> getComments(

            @Path("bookId") int bookId
    );
    @POST("comments/addComment/{accountId}")
    Call<Void> addComment(@Body Comment comment, @Path("accountId") int accountId);
}
