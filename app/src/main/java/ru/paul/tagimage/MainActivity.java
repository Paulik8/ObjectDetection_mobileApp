package ru.paul.tagimage;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ru.paul.tagimage.fragments.PostListFragment;
import ru.paul.tagimage.fragments.PostLoadFragment;

public class MainActivity extends AppCompatActivity implements OpenFragmentCallback{

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        PostLoadFragment postLoadFragment = new PostLoadFragment(this);
        fragmentManager.beginTransaction()
                .add(R.id.fragment, postLoadFragment, PostLoadFragment.TAG)
                .addToBackStack(null)
                .commit();

//        PostListFragment postListFragment = new PostListFragment();
//        fragmentManager.beginTransaction()
//                .add(R.id.fragment, postListFragment, PostListFragment.TAG)
//                .addToBackStack(null)
//                .commit();

    }

    @Override
    public void onClick(View view) {

            PostListFragment postListFragment = new PostListFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment, postListFragment, PostListFragment.TAG)
                    .addToBackStack(null)
                    .commit();

    }

}
