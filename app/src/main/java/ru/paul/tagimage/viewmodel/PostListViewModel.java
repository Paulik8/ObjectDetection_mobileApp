package ru.paul.tagimage.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.paul.tagimage.repository.PostRepository;

public class PostListViewModel extends ViewModel {
    private final LiveData<String> str;
    private PostRepository postRepository = new PostRepository();

    public PostListViewModel() {
        System.out.println("constructorPostListViewModel");
        str = postRepository.fillStr();
    }


    public LiveData<String> getStr() {
        return str;
    }
}
