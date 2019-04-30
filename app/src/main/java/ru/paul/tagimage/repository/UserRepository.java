package ru.paul.tagimage.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.paul.tagimage.service.Service;

public class UserRepository {

    private static UserRepository userRepository;
    private Service service;

    public synchronized static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    private UserRepository() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Service.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(Service.class);
    }

    public LiveData<String> getUser() {

        MutableLiveData<String> data = new MutableLiveData<>();

        service.getAuth("ku", 12).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                data.setValue("ok");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        return data;
    }
}
