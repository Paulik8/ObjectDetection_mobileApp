package ru.paul.tagimage.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PostEntity.class, UserEntity.class, ActiveEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
}
