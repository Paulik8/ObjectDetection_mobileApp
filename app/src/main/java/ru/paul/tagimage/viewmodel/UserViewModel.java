package ru.paul.tagimage.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    public void postAuth(String nick, String password, Integer age) {
        UserRepository.getInstance().getUser(nick, password, age);
    }
}
