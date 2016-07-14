package com.lzh.mdzhihudaily.module.newsList;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.base.BaseFragment;
import com.lzh.mdzhihudaily.module.newsList.model.News;
import com.lzh.mdzhihudaily.module.newsList.model.NewsListAdapter;
import com.lzh.mdzhihudaily.net.HttpMethod;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 今日日报
 *
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/13 0013 21:49
 * github: https://github.com/lisuperhong
 */
public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    private News newsList;
    private NewsListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        unsubscribe();
        subscription = HttpMethod.getInstance().dailyAPI()
                .newsLatest()
                .doOnNext(new Action1<News>() {
                    @Override
                    public void call(News news) {
                        Logger.d("测试在哪个线程");
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(News news) {
                        Logger.d(news.toString());
                        newsList = news;
                        adapter = new NewsListAdapter(getActivity(), newsList);
                        recyclerview.setAdapter(adapter);
                    }
                });
    }

    private void initView() {
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
