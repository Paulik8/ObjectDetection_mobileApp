package ru.paul.tagimage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.EmptyResultSetException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.paul.tagimage.db.ActiveEntity;
import ru.paul.tagimage.repository.UserRepository;

public class SplashActivity extends AppCompatActivity {

    CompositeDisposable disposable = new CompositeDisposable();
    SplashActivity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;

        disposable.add(UserRepository.getInstance().getUserDAO().getActiveUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ActiveEntity>() {
                    @Override
                    public void onSuccess(ActiveEntity activeEntity) {
                        if (activeEntity != null) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("activeEntity", activeEntity);
                            startActivity(intent);
                            activity.finish();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e.getClass() == EmptyResultSetException.class) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                            activity.finish();
                        }
                    }
                }));
    }
}
