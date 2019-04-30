package ru.paul.tagimage.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Author implements Serializable {

    @SerializedName("nickname")
    private String nickname;


    @SerializedName("age")
    private Integer age;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
