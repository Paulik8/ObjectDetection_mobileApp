package ru.paul.tagimage.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.paul.tagimage.model.Post;
import ru.paul.tagimage.repository.PostRepository;
import ru.paul.tagimage.repository.SearchRepository;

public class SearchViewModel extends ViewModel {

    private LiveData<List<Post>> postList;

    public SearchViewModel() {

    }

    public LiveData<List<Post>> getPosts() {
        postList = SearchRepository.getInstance().getData();
        return postList;
    }

    public void getPostList(String query, Integer page) {
        SearchRepository.getInstance().searchPosts(query, page);
    }

}
