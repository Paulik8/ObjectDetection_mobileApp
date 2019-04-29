package ru.paul.tagimage.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.paul.tagimage.entity.Post;
import ru.paul.tagimage.service.Service;

public class PostRepository {

    private static PostRepository postRepository;
    private Service service;

    public synchronized static PostRepository getInstance() {
        if (postRepository == null) {
            postRepository = new PostRepository();
        }
        return postRepository;
    }

    private PostRepository() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(Service.class);
    }

    public LiveData<List<Post>> getListPost(Integer page) {

        MutableLiveData<List<Post>> posts = new MutableLiveData<>();

        service.getPostList(page).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                System.out.println("err");
            }
        });

        return posts;
    }
}
