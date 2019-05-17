package ru.paul.tagimage.fragments;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
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
import ru.paul.tagimage.repository.PostRepository;
import ru.paul.tagimage.viewmodel.PostListViewModel;

public class PostListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "PostListFragment";
    private PostAdapter postAdapter;

    @BindView(R.id.post_list)
    RecyclerView postList;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    private Integer page = 1;
    private List<Post> postsArr = new ArrayList<>();
    private PostListViewModel postListViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.postlist, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        postList.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        refreshLayout.setOnRefreshListener(this);
        postAdapter = new PostAdapter(postList);
        postList.setAdapter(postAdapter);
        postListViewModel = ViewModelProviders.of(this).get(PostListViewModel.class);
        observeViewModel();

        initPosts();
        setListener();
    }

    private void observeViewModel() {
        postListViewModel.getPosts().observe(this, posts -> {
            refreshLayout.setRefreshing(false);
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

    private void initPosts() {
        postListViewModel.getPostList(page);
    }

    private void setListener() {
        postAdapter.setOnLoadMoreListener((() -> {
            postListViewModel.getPostListScroll(++page);
            postsArr.add(null);
            postAdapter.notifyItemInserted(postsArr.size() - 1);
        }));
    }

    @Override
    public void onRefresh() {
        page = 1;
        postListViewModel.refreshPosts(page);
    }
}
