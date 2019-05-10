package ru.paul.tagimage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.paul.tagimage.fragments.PostListFragment;
import ru.paul.tagimage.fragments.PostLoadFragment;
import ru.paul.tagimage.fragments.SearchFragment;

public class MainActivity extends AppCompatActivity implements OpenFragmentCallback, OpenSearchFragment{

    @BindView(R.id.navigation)
    BottomNavigationViewEx navigation;

    FragmentManager fragmentManager;
    PostLoadFragment postLoadFragment = new PostLoadFragment(this);
    PostListFragment postListFragment = new PostListFragment();
    SearchFragment searchFragment;
    Fragment active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        navigation.enableAnimation(false);
        navigation.enableShiftingMode(false);
        navigation.enableItemShiftingMode(false);
        navigation.setIconsMarginTop(34);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment, postListFragment, PostListFragment.TAG)
//                .addToBackStack(null)
                .commit();
        active = postListFragment;

//        PostListFragment postListFragment = new PostListFragment();
//        fragmentManager.beginTransaction()
//                .add(R.id.fragment, postListFragment, PostListFragment.TAG)
//                .addToBackStack(null)
//                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.menu_home:
                            fragmentManager.beginTransaction()
                                    .hide(active)
                                    .show(postListFragment)
                                    .commit();
                            active = postListFragment;
                            return true;
                        case R.id.menu_search:
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
                            return true;
//                        case R.id.menu_add_post:
//                            if (searchFragment == null) {
//                                searchFragment = new SearchFragment();
//                                fragmentManager.beginTransaction()
//                                        .add(R.id.fragment, searchFragment, SearchFragment.TAG)
//                                        .hide(active)
//                                        .commit();
//                            }
//                            else {
//                                fragmentManager.beginTransaction()
//                                        .hide(active)
//                                        .show(searchFragment)
//                                        .commit();
//                            }

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
