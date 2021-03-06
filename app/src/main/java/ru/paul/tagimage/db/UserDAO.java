package ru.paul.tagimage.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<PostEntity> postEntity);

    @Insert
    void insertUser(UserEntity user);

    @Query("SELECT id FROM USERENTITY WHERE username = :username and password = :password")
    Integer getUser(String username, String password);

    @Query("DELETE FROM USERENTITY")
    void clearUsers();

    @Query("SELECT * FROM ACTIVEENTITY")
    Single<ActiveEntity> getActiveUser();

    @Query("DELETE FROM ACTIVEENTITY")
    void clearActive();

    @Insert
    void insertActiveUser(ActiveEntity activeEntity);


}
