package ru.paul.tagimage;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import androidx.room.EmptyResultSetException;
import androidx.room.Room;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.paul.tagimage.db.ActiveEntity;
import ru.paul.tagimage.db.AppDatabase;
import ru.paul.tagimage.repository.UserRepository;

public class MainApplication extends Application {

    public static MainApplication instance;

    private AppDatabase db;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(this, AppDatabase.class, "database")
                .build();
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public AppDatabase getDb() {
        return db;
    }
}
