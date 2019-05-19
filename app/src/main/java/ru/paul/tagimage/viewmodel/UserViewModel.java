package ru.paul.tagimage.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.paul.tagimage.ErrorResponse;
import ru.paul.tagimage.db.ActiveEntity;
import ru.paul.tagimage.repository.UserRepository;

public class UserViewModel extends ViewModel {

    private LiveData<ActiveEntity> user = new MutableLiveData<>();

    public UserViewModel() {
//        MutableLiveData<String> userInit = new MutableLiveData<>();
//        userInit.setValue("bot1");
//        user = userInit;
        }

    public LiveData<ActiveEntity> getUser() {
        user = UserRepository.getInstance().getData();
        return user;

    }

    public void authorize(ActiveEntity activeEntity) {
        UserRepository.getInstance().authorize(activeEntity);
    }

    public void postAuth(String nick, String password, ErrorResponse errorResponse) {
        UserRepository.getInstance().getUser(nick, password, errorResponse);
    }

    public void postLogin(String nick, String password, ErrorResponse errorResponse) {
        UserRepository.getInstance().login(nick, password, errorResponse);
    }
}
