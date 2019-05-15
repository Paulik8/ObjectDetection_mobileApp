package ru.paul.tagimage;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.paul.tagimage.db.ActiveEntity;
import ru.paul.tagimage.fragments.PostListFragment;
import ru.paul.tagimage.fragments.PostLoadFragment;
import ru.paul.tagimage.fragments.SearchFragment;
import ru.paul.tagimage.repository.UserRepository;
import ru.paul.tagimage.viewmodel.MainViewModel;
import ru.paul.tagimage.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity implements OpenFragmentCallback, OpenSearchFragment{

    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Menu mainMenu;
    MainViewModel mainViewModel;
    Context context;
    SearchView searchView;
    FragmentManager fragmentManager;
    PostLoadFragment postLoadFragment;
    PostListFragment postListFragment = new PostListFragment();
    SearchFragment searchFragment;
    Fragment active;


    @Override
    public void onBackPressed() {
        if (active == searchFragment) {
            searchView.setIconified(true);
            searchView.clearFocus();
            searchView.onActionViewCollapsed();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("Home");

        context = this;
        initNavigation();

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);


        openPostListFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search");
        mainMenu = menu;
        searchView.setOnClickListener((v) -> {
        });

        searchItem.setVisible(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                mainViewModel.changeSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }



    private void initNavigation() {
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        navigation.getMenu().findItem(R.id.menu_home).setIcon(ContextCompat.getDrawable(context, R.drawable.home_filled));
        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setIconsMarginTop(34);
    }

    private void openPostListFragment() {
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment, postListFragment, PostListFragment.TAG)
                .commit();
        active = postListFragment;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_home:

//                            if (active == searchFragment) {
//                                onBackPressed();
//                            }

                            menuItem.setIcon(ContextCompat.getDrawable(context, R.drawable.home_filled));
                            navigation.getMenu().findItem(R.id.menu_search).setIcon(ContextCompat.getDrawable(context, R.drawable.search_outline));
                            navigation.getMenu().findItem(R.id.menu_add_post).setIcon(ContextCompat.getDrawable(context, R.drawable.add_post_outline));


                            fragmentManager.beginTransaction()
                                    .hide(active)
                                    .show(postListFragment)
                                    .commit();

                            mainMenu.findItem(R.id.action_search).setVisible(false);
                            searchView.setVisibility(View.GONE);
                            toolbar.setTitle("Home");

                            active = postListFragment;

                            return true;

                        case R.id.menu_search:

                            menuItem.setIcon(ContextCompat.getDrawable(context, R.drawable.search_filled));
                            navigation.getMenu().findItem(R.id.menu_home).setIcon(ContextCompat.getDrawable(context, R.drawable.home_outline));
                            navigation.getMenu().findItem(R.id.menu_add_post).setIcon(ContextCompat.getDrawable(context, R.drawable.add_post_outline));


                            if (searchFragment == null) {
                                searchFragment = new SearchFragment();
                                fragmentManager.beginTransaction()
                                        .add(R.id.fragment, searchFragment, SearchFragment.TAG)
                                        .hide(active)
                                        .commit();
                            }
                            else {
                                fragmentManager.beginTransaction()
                                        .hide(active)
                                        .show(searchFragment)
                                        .commit();
                            }
                            active = searchFragment;
                            toolbar.setTitle("Search");
                            mainMenu.findItem(R.id.action_search).setVisible(true);
                            return true;

                        case R.id.menu_add_post:

                            menuItem.setIcon(ContextCompat.getDrawable(context, R.drawable.add_post_filled));
                            navigation.getMenu().findItem(R.id.menu_search).setIcon(ContextCompat.getDrawable(context, R.drawable.search_outline));
                            navigation.getMenu().findItem(R.id.menu_home).setIcon(ContextCompat.getDrawable(context, R.drawable.home_outline));


                            if (postLoadFragment == null) {
                                postLoadFragment = new PostLoadFragment((OpenFragmentCallback) context);
                                fragmentManager.beginTransaction()
                                        .add(R.id.fragment, postLoadFragment, PostLoadFragment.TAG)
                                        .hide(active)
                                        .commit();
                            }
                            else {
                                fragmentManager.beginTransaction()
                                        .hide(active)
                                        .show(postLoadFragment)
                                        .commit();
                            }
                            active = postLoadFragment;
                            toolbar.setTitle("New Post");
                            mainMenu.findItem(R.id.action_search).setVisible(false);
                            return true;

                        case R.id.menu_logout:
                            UserRepository.getInstance().getExecutorService().execute(() -> {
                                UserRepository.getInstance().getUserDAO().clearActive();
                                    }
                            );

//                            UserViewModel userViewModel = ViewModelProviders.of((LoginActivity)ActivitiesBus.getInstance().getContext()).get(UserViewModel.class);
                            UserRepository.getInstance().deleteInstance();
                            Intent intent = new Intent(context, LoginActivity.class);
                            startActivity(intent);
                            ((MainActivity)(context)).finish();
//                            onDestroy();
                            return true;
                    }
                    return false;
                }
            };

    @Override
    public void onClick(View view) {

            PostListFragment postListFragment = new PostListFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, postListFragment, PostListFragment.TAG)
//                    .addToBackStack(null)
                    .commit();

    }

}
