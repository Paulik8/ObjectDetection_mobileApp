package ru.paul.tagimage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiity_login);
        ButterKnife.bind(this);

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser().observe(this, user -> {
            Log.i("key", user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {

            userViewModel.postAuth(nickname.getText().toString(), password.getText().toString(), Integer.parseInt((age.getText().toString())));
        });
    }


}
