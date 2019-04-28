package ru.paul.tagimage.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class PostRepository {

    public LiveData<String> fillStr() {
        MutableLiveData<String> data = new MutableLiveData<>();
        data.setValue("ku");
        return data;
    }
}
