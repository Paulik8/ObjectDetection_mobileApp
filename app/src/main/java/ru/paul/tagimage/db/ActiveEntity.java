package ru.paul.tagimage.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ActiveEntity {

    @PrimaryKey
    public Integer id;

    public String nickname;

    public String password;
}

