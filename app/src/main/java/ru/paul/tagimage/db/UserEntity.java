package ru.paul.tagimage.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserEntity {
    @PrimaryKey
    public Integer id;

    public String username;

    public String password;

    public Integer age;
}
