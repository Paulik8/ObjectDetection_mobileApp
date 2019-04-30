package ru.paul.tagimage.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.paul.tagimage.model.Post;

public interface Service {

    String BASE_URL = "http://192.168.1.65:3000/";

    @GET("posts")
    Call<List<Post>> getPostList (@Query("page") Integer page);

    @POST("auth")
    Call<String> getAuth(@Header ("Authorization") String auth, @Body Integer age);
}
