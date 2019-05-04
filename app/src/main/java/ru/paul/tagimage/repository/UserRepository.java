package ru.paul.tagimage.repository;

import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.paul.tagimage.MainApplication;
import ru.paul.tagimage.db.UserDAO;
import ru.paul.tagimage.db.UserEntity;
import ru.paul.tagimage.model.ApiResponse;
import ru.paul.tagimage.service.Service;

public class UserRepository {

    private static UserRepository userRepository;
    private Service service;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final UserDAO userDAO = MainApplication.getInstance().getDb().userDAO();
    private MutableLiveData<String> data = new MutableLiveData<>();
    private MutableLiveData<Integer> idUser;

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

    public LiveData<String> getData() {
        return data;
    }

    public void getUser(String nick, String password, Integer age) {


        byte[] encoded = (nick + ":" + password).getBytes(StandardCharsets.UTF_8);
        String base64 = "Basic " + Base64.encodeToString(encoded, Base64.NO_WRAP);
//        ApiResponse requestAuth = new ApiResponse(age);

        service.getAuth(base64, age).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull  Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {

                executorService.execute(() -> {
                    UserEntity user = new UserEntity();
                    user.username = nick;
                    user.password = password;
                    user.age = age;
                    userDAO.insertUser(user);
//                    Log.i("idUser", userDAO.getUser(nick, password).toString());
                });

                data.setValue("ok");

                Log.i("key2", data.getValue());
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call,@NonNull Throwable t) {
                Log.i("err", "err");
            }
        });
    }
}
