package ru.paul.tagimage.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.paul.tagimage.model.Post;
import ru.paul.tagimage.repository.PostRepository;

public class PostListViewModel extends ViewModel {
    private final LiveData<List<Post>> postList;

    public PostListViewModel() {

        postList = PostRepository.getInstance().getListPost(1);
    }


    public LiveData<List<Post>> getPostList() {
        return postList;
    }
}
