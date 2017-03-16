package com.lzh.mdzhihudaily_mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lzh.mdzhihudaily_mvp.R;
import com.lzh.mdzhihudaily_mvp.base.BaseActivity;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeEntity;
import com.lzh.mdzhihudaily_mvp.ui.fragment.NavigationFragment;
import com.lzh.mdzhihudaily_mvp.ui.fragment.NewsListFragment;
import com.lzh.mdzhihudaily_mvp.ui.fragment.ThemeDailyFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String NEWS_FRAGMENT = "news";
    private static final String THEME_FRAGMENT = "theme";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    private String currentIndex;
    private ThemeEntity.Theme theme;
    private FragmentManager fragmentManager;
    private NavigationFragment navigationFragment;
    private NewsListFragment newsListFragment;
    private ThemeDailyFragment themeDailyFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fragmentManager = getSupportFragmentManager();
        navigationFragment = new NavigationFragment();
        fragmentManager.beginTransaction().add(R.id.left_menu_container, navigationFragment).commit();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getString("index");
            newsListFragment = (NewsListFragment) fragmentManager.findFragmentByTag(NEWS_FRAGMENT);
            themeDailyFragment = (ThemeDailyFragment) fragmentManager.findFragmentByTag(THEME_FRAGMENT);
            setCurrentFragment(currentIndex);
        } else {
            setCurrentFragment(NEWS_FRAGMENT);
        }
    }

    @Override
    protected void initData() {
        navigationFragment.setOnMenuItemSelectedListener(new NavigationFragment.OnMenuItemSelectedListener() {
            @Override
            public void menuItemSelected(int position, ThemeEntity.Theme themeItem) {
                if (position == 0) {
                    setCurrentFragment(NEWS_FRAGMENT);
                } else {
                    theme = themeItem;
                    setCurrentFragment(THEME_FRAGMENT);
                }
            }
        });
    }

    private void setCurrentFragment(String index) {
        if (!index.equals(currentIndex) || index.equals(THEME_FRAGMENT)) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            switch (index) {
                case NEWS_FRAGMENT:
                    toolbar.setTitle("首页");
                    newsListFragment = new NewsListFragment();
                    transaction.replace(R.id.content_container, newsListFragment, NEWS_FRAGMENT);
                    break;
                case THEME_FRAGMENT:
                    toolbar.setTitle(theme.getName());
                    themeDailyFragment = ThemeDailyFragment.newInstance(theme.getName(), theme.getId());
                    transaction.replace(R.id.content_container, themeDailyFragment, THEME_FRAGMENT);
                    break;
            }
            invalidateOptionsMenu();
            transaction.commit();
            currentIndex = index;
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //如果在当前页
            drawer.closeDrawer(GravityCompat.START);
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return (id == R.id.action_settings);
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

    public Toolbar getToolbar() {
        return toolbar;
    }
}
