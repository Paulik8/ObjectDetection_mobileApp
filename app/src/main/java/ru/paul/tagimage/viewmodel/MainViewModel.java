package ru.paul.tagimage.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> search = new MutableLiveData<>();

    public LiveData<String> getSearch() {
        return search;
    }

    public void changeSearch(String value) {
        search.setValue(value);
        Log.i("search", "search");
    }
}
