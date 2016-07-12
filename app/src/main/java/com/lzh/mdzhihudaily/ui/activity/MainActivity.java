package com.lzh.mdzhihudaily.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.ui.fragment.NavigationFragment;
import com.lzh.mdzhihudaily.ui.fragment.NewsListFragment;
import com.lzh.mdzhihudaily.ui.fragment.ThemeDailyFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String NEWS_FRAGMENT = "news";
    private static final String THEME_FRAGMENT = "theme";

    private String currentIndex;
    private String themeDaily;
    private FragmentManager fragmentManager;
    private NavigationFragment navigationFragment;
    private NewsListFragment newsListFragment;
    private ThemeDailyFragment themeDailyFragment;

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();
        navigationFragment = new NavigationFragment();
        fragmentManager.beginTransaction().add(R.id.left_menu_container, navigationFragment).commit();

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getString("index");
            newsListFragment = (NewsListFragment) fragmentManager.findFragmentByTag(NEWS_FRAGMENT);
            themeDailyFragment = (ThemeDailyFragment) fragmentManager.findFragmentByTag(THEME_FRAGMENT);
            setCurrentFramgent(currentIndex);
        } else {
            setCurrentFramgent(NEWS_FRAGMENT);
        }

        setListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("index", currentIndex);
        super.onSaveInstanceState(outState);
    }

    private void setListener() {
        navigationFragment.setOnMenuItemSelectedListener(new NavigationFragment.OnMenuItemSelectedListener() {
            @Override
            public void menuItemSelected(int position, String theme) {
                themeDaily = theme;
                if (position == 0) {
                    setCurrentFramgent(NEWS_FRAGMENT);
                } else {
                    Log.d("menuItemSelected", "THEME_FRAGMENT");
                    setCurrentFramgent(THEME_FRAGMENT);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setCurrentFramgent(String index) {
        if (currentIndex != index || index == THEME_FRAGMENT) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (index) {
                case NEWS_FRAGMENT:
                    newsListFragment = new NewsListFragment();
                    transaction.replace(R.id.content_container, newsListFragment, NEWS_FRAGMENT);
                    break;
                case THEME_FRAGMENT:
                    themeDailyFragment = new ThemeDailyFragment().newInstance(themeDaily);
                    transaction.replace(R.id.content_container, themeDailyFragment, THEME_FRAGMENT);
                    break;
            }
            transaction.commit();
            currentIndex = index;
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            //如果在当前页
            drawer.closeDrawer(Gravity.LEFT);
            return;
        }
    }

}
