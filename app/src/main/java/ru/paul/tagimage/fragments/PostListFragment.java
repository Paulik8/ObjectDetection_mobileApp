package ru.paul.tagimage.fragments;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        PostAdapter postAdapter = new PostAdapter();
        postList.setAdapter(postAdapter);
        PostListViewModel viewModel = ViewModelProviders.of(this).get(PostListViewModel.class);
        observeViewModel(viewModel);
    }

    private void observeViewModel(PostListViewModel postListViewModel) {
        postListViewModel.getPostList().observe(this, posts -> {
            System.out.println("kek");
        });

    }
}
