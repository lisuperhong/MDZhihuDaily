package com.lzh.mdzhihudaily.module.themeDaily;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.appinterface.RecyclerviewItemListener;
import com.lzh.mdzhihudaily.base.BaseFragment;
import com.lzh.mdzhihudaily.module.newsDetail.NewsDetailActivity;
import com.lzh.mdzhihudaily.module.themeDaily.model.ThemeDailyListAdapter;
import com.lzh.mdzhihudaily.module.themeDaily.model.ThemeNews;
import com.lzh.mdzhihudaily.net.HttpMethod;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/11 0011 22:24
 * github: https://github.com/lisuperhong
 */
public class ThemeDailyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerviewItemListener {

    private static final String KEY_THEME = "key_theme";
    private static final String THEME_ID = "theme_id";

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    private ThemeDailyListAdapter adapter;
    private ThemeNews themeNews;
    private int themeId;

    public static ThemeDailyFragment newInstance(String theme, int id) {
        ThemeDailyFragment themeDailyFragment = new ThemeDailyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_THEME, theme);
        bundle.putInt(THEME_ID, id);
        themeDailyFragment.setArguments(bundle);
        return themeDailyFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme_daily, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        refreshLayout.setOnRefreshListener(this);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void initData() {
        themeId = (int) getArguments().get(THEME_ID);
        adapter = new ThemeDailyListAdapter(getActivity(), new ThemeNews());
        adapter.setItemClickListener(this);
        unsubscribe();
        subscription = HttpMethod.getInstance().dailyAPI()
                .themeNews(themeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ThemeNews>() {

                    @Override
                    public void onStart() {
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(ThemeNews themeNews) {
                        adapter.setThemeNews(themeNews);
                        recyclerview.setAdapter(adapter);
                    }
                });
    }

    @Override
    public void onRefresh() {
        unsubscribe();
        subscription = HttpMethod.getInstance().dailyAPI()
                .themeNews(themeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ThemeNews>() {
                    @Override
                    public void onCompleted() {
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(ThemeNews themeNews) {
                        refreshLayout.setRefreshing(false);
                        adapter.setThemeNews(themeNews);
                    }
                });
    }

    @Override
    public void onItemClick(long storyId) {
        NewsDetailActivity.startActivity(getActivity(), storyId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
