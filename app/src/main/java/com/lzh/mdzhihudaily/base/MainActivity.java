package com.lzh.mdzhihudaily.base;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.module.newsList.NewsListFragment;
import com.lzh.mdzhihudaily.module.themeDaily.ThemeDailyFragment;

public class MainActivity extends AppCompatActivity {

    private static final String NEWS_FRAGMENT = "news";
    private static final String THEME_FRAGMENT = "theme";

    private String currentIndex;
    private String themeDaily;
    private FragmentManager fragmentManager;
    private NavigationFragment navigationFragment;
    private NewsListFragment newsListFragment;
    private ThemeDailyFragment themeDailyFragment;

    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

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

    private void setListener() {
        navigationFragment.setOnMenuItemSelectedListener(new NavigationFragment.OnMenuItemSelectedListener() {
            @Override
            public void menuItemSelected(int position, String theme) {
                themeDaily = theme;
                if (position == 0) {
                    setCurrentFramgent(NEWS_FRAGMENT);
                } else {
                    setCurrentFramgent(THEME_FRAGMENT);
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("index", currentIndex);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
                    toolbar.setTitle(themeDaily);
                    newsListFragment = new NewsListFragment();
                    transaction.replace(R.id.content_container, newsListFragment, NEWS_FRAGMENT);
                    break;
                case THEME_FRAGMENT:
                    toolbar.setTitle(themeDaily);
                    themeDailyFragment = new ThemeDailyFragment().newInstance(themeDaily);
                    transaction.replace(R.id.content_container, themeDailyFragment, THEME_FRAGMENT);
                    break;
            }
            invalidateOptionsMenu();
            transaction.commit();
            currentIndex = index;
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            //如果在当前页
            drawer.closeDrawer(Gravity.LEFT);
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (currentIndex.equals(NEWS_FRAGMENT)) {
            menu.findItem(R.id.action_notification).setVisible(true);
            menu.findItem(R.id.action_add).setVisible(false);
            menu.findItem(R.id.action_dark_mode).setVisible(true);
            menu.findItem(R.id.action_settings).setVisible(true);
        } else {
            menu.findItem(R.id.action_notification).setVisible(false);
            menu.findItem(R.id.action_add).setVisible(true);
            menu.findItem(R.id.action_dark_mode).setVisible(false);
            menu.findItem(R.id.action_settings).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }

}
