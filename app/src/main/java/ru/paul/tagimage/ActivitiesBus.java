package ru.paul.tagimage;

import android.app.Activity;

import ru.paul.tagimage.viewmodel.UserViewModel;

public class ActivitiesBus {

    private static ActivitiesBus activitiesBus;

    private Activity context;
    private UserViewModel userViewModel;


    public UserViewModel getUserViewModel() {
        return userViewModel;
    }

    public void setUserViewModel(UserViewModel userViewModel) {
        this.userViewModel = userViewModel;
    }

    public synchronized static ActivitiesBus getInstance() {
        if (activitiesBus == null) {
            activitiesBus = new ActivitiesBus();
        }
        return activitiesBus;
    }

    public Activity getContext() {
        return context;
    }

    public void setContext(Activity activity) {
        this.context = activity;
    }
}
