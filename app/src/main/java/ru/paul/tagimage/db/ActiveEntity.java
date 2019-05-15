package ru.paul.tagimage.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class ActiveEntity implements Serializable {

    @PrimaryKey
    public Integer id;

    public String nickname;

    public String password;
}

