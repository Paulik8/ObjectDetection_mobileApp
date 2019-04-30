package ru.paul.tagimage.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.paul.tagimage.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private LiveData<String> user;

    public UserViewModel() {
        MutableLiveData<String> userInit = new MutableLiveData<>();
        userInit.setValue("bot1");
        user = userInit;
        }

    public LiveData<String> getUser() {
        return user;

    }

    public void postAuth() {
        user = UserRepository.getInstance().getUser();
    }
}
