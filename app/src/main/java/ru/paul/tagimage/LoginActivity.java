package ru.paul.tagimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.EmptyResultSetException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.paul.tagimage.db.ActiveEntity;
import ru.paul.tagimage.repository.UserRepository;
import ru.paul.tagimage.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login)
    Button loginButton;
    @BindView(R.id.nick)
    EditText nickname;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.age)
    EditText age;
    ActiveEntity activeUser;
    public Observer observer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiity_login);
        ButterKnife.bind(this);


        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        ActivitiesBus.getInstance().setContext(this);
        ActivitiesBus.getInstance().setUserViewModel(userViewModel);

        Intent intent = getIntent();
        ActiveEntity activeEntity = (ActiveEntity) intent.getSerializableExtra("activeEntity");
        if (activeEntity != null) {
            userViewModel.authorize(activeEntity);
            Log.i("activeUser", String.valueOf(activeEntity));
        }
//        observer = new Observer<ActiveEntity>() {
//
//            @Override
//            public void onChanged(ActiveEntity activeEntity) {
//                Intent intent = new Intent(ActivitiesBus.getInstance().getContext(), MainActivity.class);
//                startActivity(intent);
//                (ActivitiesBus.getInstance().getContext()).finish();
//                Log.i("observe", "observe");
//            }
//        };
//        userViewModel.getUser().observe(this, observer);
        userViewModel.getUser().observe(this, user -> {
            Log.i("activeUser0", "observe");
            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
            this.finish();
        });

        loginButton.setOnClickListener(view ->
                userViewModel.postAuth(nickname.getText().toString(), password.getText().toString(), Integer.parseInt((age.getText().toString()))));
        Log.i("activeUser2", String.valueOf("null"));

//        UserRepository.getInstance().getExecutorService().execute(() ->
//                activeUser = UserRepository.getInstance().getUserDAO().getActiveUser());


//        if (activeUser != null) {
//            userViewModel.authorize(activeUser);
//            Log.i("activeUser", String.valueOf(activeUser));
//        }
//        else {
//            loginButton.setOnClickListener(view ->
//                    userViewModel.postAuth(nickname.getText().toString(), password.getText().toString(), Integer.parseInt((age.getText().toString()))));
//            Log.i("activeUser2", String.valueOf(activeUser));
//        }

    }

}
