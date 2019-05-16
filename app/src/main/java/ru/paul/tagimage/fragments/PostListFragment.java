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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.paul.tagimage.OnLoadMoreListener;
import ru.paul.tagimage.R;
import ru.paul.tagimage.adapter.PostAdapter;
import ru.paul.tagimage.model.Post;
import ru.paul.tagimage.viewmodel.PostListViewModel;

public class PostListFragment extends Fragment {
    public static final String TAG = "PostListFragment";
    private PostAdapter postAdapter;

    @BindView(R.id.post_list)
    RecyclerView postList;
    Integer page = 1;
    List<Post> postsArr = new ArrayList<>();

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
        postAdapter = new PostAdapter(postList);
        postList.setAdapter(postAdapter);
        PostListViewModel viewModel = ViewModelProviders.of(this).get(PostListViewModel.class);
        observeViewModel(viewModel);

        initPosts(viewModel);
        setListener(viewModel);
    }

    private void observeViewModel(PostListViewModel postListViewModel) {
        postListViewModel.getPosts().observe(this, posts -> {
            Log.i("posts", "observe");
            if (postsArr.size() > 0) {
                postsArr.remove(postsArr.size() - 1);
                postAdapter.notifyItemRemoved(postsArr.size());
            }
            if (postsArr.size() > 0) {
                postsArr.clear();
            }
            postsArr.addAll(posts);
            postAdapter.setLoaded();
            postAdapter.setPosts(postsArr);
            postAdapter.notifyDataSetChanged();
        });

    }

    private void initPosts(PostListViewModel postListViewModel) {
        postListViewModel.getPostList(page);
    }

    private void setListener(PostListViewModel postListViewModel) {
        postAdapter.setOnLoadMoreListener((new OnLoadMoreListener() {
            @Override
            public void OnLoadMore() {
                postListViewModel.getPostListScroll(++page);
                postsArr.add(null);
                postAdapter.notifyItemInserted(postsArr.size() - 1);
            }
        }));
    }
}
