package ru.paul.tagimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.EmptyResultSetException;

import com.rengwuxian.materialedittext.MaterialEditText;

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

public class LoginActivity extends AppCompatActivity implements ErrorResponse{

    @BindView(R.id.login)
    Button loginButton;
    @BindView(R.id.login_in)
    Button loginInButton;
    @BindView(R.id.nick)
    MaterialEditText nickname;
    @BindView(R.id.password)
    MaterialEditText password;
//    @BindView(R.id.age)
//    MaterialEditText age;
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

        loginButton.setOnClickListener(view -> {

                if (nickname.getText().toString().equals("") || password.getText().toString().equals("")) {
                    nickname.setError("Not filled");
            }
                else {
                    userViewModel.postAuth(nickname.getText().toString(), password.getText().toString(), this);
                }

            Log.i("activeUser2", String.valueOf("null"));
        });

        loginInButton.setOnClickListener(view -> {

            if (nickname.getText().toString().equals("")|| password.getText().toString().equals("")) {
                nickname.setError("Not filled");
            }
            else {
                userViewModel.postLogin(nickname.getText().toString(), password.getText().toString(), this);
            }

            Log.i("activeUser2", String.valueOf("null"));
        });
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

    @Override
    public void error(Integer code) {
        showPopUp(code);
    }

    private void showPopUp(Integer code) {
//        LayoutInflater inflater = (LayoutInflater)
//                getSystemService(LAYOUT_INFLATER_SERVICE);
        LayoutInflater layoutInflater = getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.popup_load, null);
        TextView textView = popupView.findViewById(R.id.text_load);
        if (code == 409)
            textView.setText(R.string.duplicate_error);
        else if (code == 401)
            textView.setText(R.string.unauthorized_error);

        hideKeyboardFrom();

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        View v = layoutInflater.inflate(R.layout.actiity_login, null);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        closePopUp(popupWindow);

        // dismiss the popup window when touched
    }

    private void closePopUp(PopupWindow pop) {
        Handler handler = new Handler();
        handler.postDelayed(pop::dismiss, 2000);
    }

    public void hideKeyboardFrom() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
    }
}
