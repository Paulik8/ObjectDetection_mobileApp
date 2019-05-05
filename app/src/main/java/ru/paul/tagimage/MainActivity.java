package ru.paul.tagimage;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import ru.paul.tagimage.fragments.PostListFragment;
import ru.paul.tagimage.fragments.PostLoadFragment;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        PostLoadFragment postLoadFragment = new PostLoadFragment();
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
}
