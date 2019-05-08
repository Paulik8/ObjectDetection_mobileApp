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

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.paul.tagimage.R;
import ru.paul.tagimage.adapter.PostAdapter;
import ru.paul.tagimage.adapter.SearchAdapter;
import ru.paul.tagimage.viewmodel.PostListViewModel;
import ru.paul.tagimage.viewmodel.SearchViewModel;

public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";
    private SearchAdapter searchAdapter;

    @BindView(R.id.search_list)
    RecyclerView searchList;

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
        searchAdapter = new SearchAdapter();
        searchList.setAdapter(searchAdapter);
        SearchViewModel viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        observeViewModel(viewModel);

        initPosts(viewModel,1);
    }

    private void observeViewModel(SearchViewModel searchViewModel) {
        searchViewModel.getPosts().observe(this, posts -> {
            Log.i("posts", "observe");
            searchAdapter.setPosts(posts);
            searchAdapter.notifyDataSetChanged();
        });

    }

    private void initPosts(SearchViewModel searchViewModel, Integer page) {
        searchViewModel.getPostList(page);
    }
}
