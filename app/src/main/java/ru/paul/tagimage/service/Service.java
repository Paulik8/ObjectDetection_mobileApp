package ru.paul.tagimage.service;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.paul.tagimage.model.ApiResponse;
import ru.paul.tagimage.model.Post;

public interface Service {

    String BASE_URL = "http://192.168.1.65:3000/";
//    String BASE_URL = "http://172.16.91.166:3000/";

    @Multipart
    @POST("post")
    Call<ApiResponse> loadPost(@Header("Authorization") String auth, @Part MultipartBody.Part file, @Part("author") RequestBody author, @Part("caption") RequestBody caption, @Part("data") RequestBody date);

    @GET("posts")
    Call<List<Post>> getPostList (@Query("page") Integer page);

    @GET("search/{tags}")
    Call<List<Post>> searchPosts (@Path ("tags") String tags, @Query("page") Integer page);

    @FormUrlEncoded
    @POST("auth")
    Call<ApiResponse> getAuth(@Header("Authorization") String auth, @Field("age") Integer age);

    @POST("login")
    Call<ApiResponse> getLogin(@Header("Authorization") String auth);
}
