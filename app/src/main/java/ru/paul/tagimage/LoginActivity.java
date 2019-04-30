package ru.paul.tagimage;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.paul.tagimage.viewmodel.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login)
    Button loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiity_login);
        ButterKnife.bind(this);

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser().observe(this, user -> {

        });

        loginButton.setOnClickListener(view -> {
            userViewModel.postAuth();
        });
    }


}
