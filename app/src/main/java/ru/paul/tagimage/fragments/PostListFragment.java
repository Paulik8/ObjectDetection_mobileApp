package ru.paul.tagimage.fragments;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.paul.tagimage.R;
import ru.paul.tagimage.adapter.PostAdapter;
import ru.paul.tagimage.viewmodel.PostListViewModel;

public class PostListFragment extends Fragment {
    public static final String TAG = "PostListFragment";
    private PostAdapter postAdapter;

    @BindView(R.id.post_list)
    RecyclerView postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.postlist, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postList.setLayoutManager(new LinearLayoutManager(getContext()));
        postAdapter = new PostAdapter();
        postList.setAdapter(postAdapter);
        PostListViewModel viewModel = ViewModelProviders.of(this).get(PostListViewModel.class);
        observeViewModel(viewModel);

        initPosts(viewModel,1);
    }

    private void observeViewModel(PostListViewModel postListViewModel) {
        postListViewModel.getPosts().observe(this, posts -> {
            Log.i("posts", "observe");
            postAdapter.setPosts(posts);
            postAdapter.notifyDataSetChanged();
        });

    }

    private void initPosts(PostListViewModel postListViewModel, Integer page) {
        postListViewModel.getPostList(page);
    }
}
