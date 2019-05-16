package ru.paul.tagimage.viewmodel;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.paul.tagimage.model.Post;
import ru.paul.tagimage.repository.PostRepository;

public class PostListViewModel extends ViewModel {
    private LiveData<List<Post>> postList;

    public PostListViewModel() {

    }

    public LiveData<List<Post>> getPosts() {
        postList = PostRepository.getInstance().getData();
        return postList;
    }

    public void getPostList(Integer page) {
        PostRepository.getInstance().getListPost(page);
    }

    public void getPostListScroll(Integer page) {
        PostRepository.getInstance().getListPostScroll(page);
    }
}
