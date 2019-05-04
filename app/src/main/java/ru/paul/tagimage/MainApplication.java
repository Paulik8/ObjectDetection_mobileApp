package ru.paul.tagimage;

import android.app.Application;

import androidx.room.Room;

import ru.paul.tagimage.db.AppDatabase;

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
