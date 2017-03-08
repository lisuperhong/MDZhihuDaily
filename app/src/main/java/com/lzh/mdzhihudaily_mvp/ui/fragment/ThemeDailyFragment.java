package com.lzh.mdzhihudaily_mvp.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.lzh.mdzhihudaily_mvp.R;
import com.lzh.mdzhihudaily_mvp.base.BaseFragment;
import com.lzh.mdzhihudaily_mvp.base.RecyclerviewItemListener;
import com.lzh.mdzhihudaily_mvp.contract.ThemeDailyContract;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeNews;
import com.lzh.mdzhihudaily_mvp.presenter.ThemeDailyPresenter;
import com.lzh.mdzhihudaily_mvp.ui.activity.ThemeNewsDetailActivity;
import com.lzh.mdzhihudaily_mvp.ui.adapter.ThemeDailyListAdapter;

import butterknife.BindView;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/3/5 14:40
 * @github: https://github.com/lisuperhong
 */

public class ThemeDailyFragment extends BaseFragment implements ThemeDailyContract.View, SwipeRefreshLayout.OnRefreshListener,
        RecyclerviewItemListener {

    private static final String KEY_THEME = "key_theme";
    private static final String THEME_ID = "theme_id";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private ThemeDailyListAdapter adapter;

    public static ThemeDailyFragment newInstance(String theme, int id) {
        ThemeDailyFragment themeDailyFragment = new ThemeDailyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_THEME, theme);
        bundle.putInt(THEME_ID, id);
        themeDailyFragment.setArguments(bundle);
        return themeDailyFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme_daily;
    }

    @Override
    protected void initView() {
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        refreshLayout.setOnRefreshListener(this);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        int themeId = (int) getArguments().get(THEME_ID);
        adapter = new ThemeDailyListAdapter(getActivity(), new ThemeNews());
        adapter.setItemClickListener(this);
        recyclerview.setAdapter(adapter);

        presenter = new ThemeDailyPresenter(this, themeId);
        presenter.start();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void stopRefreshLayout() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void setData(ThemeNews themeNews) {
        adapter.setThemeNews(themeNews);
    }

    @Override
    public void onRefresh() {
        ((ThemeDailyPresenter) presenter).refreshData();
    }

    @Override
    public void onItemClick(long storyId) {
        ThemeNewsDetailActivity.startActivity(getActivity(), storyId);
    }

}
