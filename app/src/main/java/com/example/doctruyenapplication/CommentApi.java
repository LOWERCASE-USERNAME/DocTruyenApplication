package com.example.doctruyenapplication;


import com.example.doctruyenapplication.Comment;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommentApi {

    // Endpoint để lấy danh sách bình luận của một truyện theo ID
    @GET("comments/{storyId}")
    Call<List<Comment>> getComments(@Path("storyId") int storyId);

    // Endpoint để thêm bình luận mới
    @POST("comments")
    Call<Comment> postComment(@Body Comment comment);
}
