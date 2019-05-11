package ru.paul.tagimage.repository;

import android.util.Log;

import androidx.annotation.NonNull;
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
import ru.paul.tagimage.model.Post;
import ru.paul.tagimage.service.Service;

public class SearchRepository {

    private static SearchRepository searchRepository;
    private MutableLiveData<List<Post>> data = new MutableLiveData<>();
    private Service service;

    public synchronized static SearchRepository getInstance() {
        if (searchRepository == null) {
            searchRepository = new SearchRepository();
        }
        return searchRepository;
    }

    private SearchRepository() {

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(Service.class);
    }

    public LiveData<List<Post>> getData() {
        return data;
    }

    public void searchPosts(String query, Integer page) {

        service.searchPosts(query, page).enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                data.setValue(response.body());
                Log.i("posts", "ok");
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                Log.e("posts", "err");
            }
        });

    }

}
