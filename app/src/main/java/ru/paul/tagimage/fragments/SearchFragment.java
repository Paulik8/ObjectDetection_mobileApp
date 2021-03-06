package ru.paul.tagimage.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.paul.tagimage.OnLoadMoreListener;
import ru.paul.tagimage.R;
import ru.paul.tagimage.adapter.PostAdapter;
import ru.paul.tagimage.adapter.SearchAdapter;
import ru.paul.tagimage.model.Post;
import ru.paul.tagimage.viewmodel.MainViewModel;
import ru.paul.tagimage.viewmodel.PostListViewModel;
import ru.paul.tagimage.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";
    private SearchAdapter searchAdapter;

    @BindView(R.id.search_list)
    RecyclerView searchList;
    private Integer page = 1;
    private List<Post> postsArr = new ArrayList<>();
    private String searchRes = "cats";

    public void setPage(Integer page) {
        this.page = page;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchList.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(searchList);
        searchList.setAdapter(searchAdapter);
        MainViewModel mainViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        SearchViewModel viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        observeSearchViewModel(viewModel);
        observeMainViewModel(mainViewModel, viewModel);

        initPosts(viewModel);
        setListener(viewModel);
    }

    private void observeSearchViewModel(SearchViewModel searchViewModel) {
        searchViewModel.getPosts().observe(this, posts -> {
            Log.i("posts", "observe");
            if (postsArr.size() > 0) {
                postsArr.remove(postsArr.size() - 1);
                searchAdapter.notifyItemRemoved(postsArr.size());
            }
            if (postsArr.size() > 0) {
                postsArr.clear();
            }
            searchAdapter.setLoaded();
            if (posts != null) {
                postsArr.addAll(posts);
            }

            searchAdapter.setPosts(posts);
            searchAdapter.notifyDataSetChanged();
        });

    }

    private void observeMainViewModel(MainViewModel mainViewModel, SearchViewModel searchViewModel) {
        mainViewModel.getSearch().observe(this, search -> {
            searchRes = search;
            searchViewModel.getPostList(search, page);
        });
    }

    private void initPosts(SearchViewModel searchViewModel) {
        searchViewModel.getPostList(searchRes, page);
    }

    private void setListener(SearchViewModel searchViewModel) {
        searchAdapter.setOnLoadMoreListener((() -> {
            searchViewModel.getPostListScroll(searchRes, ++page);
            postsArr.add(null);
            searchAdapter.notifyItemInserted(postsArr.size() - 1);
        }));
    }
}
