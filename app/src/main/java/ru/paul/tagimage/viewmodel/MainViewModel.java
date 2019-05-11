package ru.paul.tagimage.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> search;

    public LiveData<String> getSearch() {
        return search;
    }

    public void changeSearch(String value) {
        search.setValue(value);
    }
}
