package com.lzh.mdzhihudaily.module.newsList;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.lzh.mdzhihudaily.R;
import com.lzh.mdzhihudaily.appinterface.RecyclerviewItemListener;
import com.lzh.mdzhihudaily.base.BaseFragment;
import com.lzh.mdzhihudaily.base.MainActivity;
import com.lzh.mdzhihudaily.module.newsDetail.NewsDetailActivity;
import com.lzh.mdzhihudaily.module.newsList.model.News;
import com.lzh.mdzhihudaily.module.newsList.model.NewsListAdapter;
import com.lzh.mdzhihudaily.net.HttpMethod;
import com.lzh.mdzhihudaily.utils.DateUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 今日日报
 *
 * @author 李昭鸿
 * @description
 * @date Created on 2016/7/13 0013 21:49
 * github: https://github.com/lisuperhong
 */
public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, RecyclerviewItemListener {

    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.progress)
    ProgressBar progressBar;

    private NewsListAdapter adapter;
    private String currentDate;
    private String beforeDate;
    private boolean isLoadMore = false;

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
        currentDate = DateUtil.dateToString(DateUtil.FORMAT_YMD, DateUtil.getCurrentDate());
        beforeDate = currentDate;
        adapter = new NewsListAdapter(getActivity(), new ArrayList<News.Story>(), new ArrayList<News.TopStory>());
        adapter.setItemClickListener(this);
        recyclerview.setAdapter(adapter);

        unsubscribe();
        subscription = HttpMethod.getInstance().dailyAPI()
                .newsLatest()
                .map(getNewStories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<News>() {

                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(News news) {
                        adapter.setRefreshDate(news.getStories(), news.getTopStories());
                    }
                });
    }

    private void initView() {
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        refreshLayout.setOnRefreshListener(this);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isLoadMore) {
                    return true;
                }
                return false;
            }
        });
        recyclerview.addOnScrollListener(new ListScrollListener());
    }

    @Override
    public void onRefresh() {
        unsubscribe();
        subscription = HttpMethod.getInstance().dailyAPI()
                .newsLatest()
                .map(getNewStories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News>() {
                    @Override
                    public void onCompleted() {
                        refreshLayout.setRefreshing(false);
                        beforeDate =  currentDate;
                        Logger.d("refresh " + beforeDate);
                    }

                    @Override
                    public void onError(Throwable e) {
                        refreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onNext(News news) {
                        adapter.setRefreshDate(news.getStories(), news.getTopStories());
                    }
                });
    }


    private void loadMore() {
        unsubscribe();
        Logger.d("loadMore " + beforeDate);
        subscription = HttpMethod.getInstance().dailyAPI()
                .newBefore(beforeDate)
                .map(getNewStories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<News>() {

                    @Override
                    public void onCompleted() {
                        isLoadMore = false;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(News news) {
                        beforeDate = news.getDate();
                        adapter.setBeforeNews(news.getStories());
                    }
                });

    }

    private class ListScrollListener extends RecyclerView.OnScrollListener {

        String toolbarTitle = "";

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int firstVisibilityItem = manager.findFirstVisibleItemPosition();
            int lastVisibilityItem = manager.findLastVisibleItemPosition();
            int totalItemCount = manager.getItemCount();

            if (firstVisibilityItem == 0) {
                toolbarTitle = "首页";
                ((MainActivity) getActivity()).getToolbar().setTitle(toolbarTitle);
            } else if (!toolbarTitle.equals(adapter.getStories().get(firstVisibilityItem - 1))) {
                toolbarTitle = adapter.getStories().get(firstVisibilityItem - 1).getStoryDate();
                ((MainActivity) getActivity()).getToolbar().setTitle(toolbarTitle);
            }

            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibilityItem == (totalItemCount - 1)) {
                isLoadMore = true;
                loadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }

    private Func1<News, News> getNewStories = new Func1<News, News>() {
        @Override
        public News call(News news) {
            Logger.d(news);
            List<News.Story> newStories = new ArrayList<>();
            String dateString = news.getDate();
            if (dateString.equals(currentDate)) {
                for (News.Story story : news.getStories()) {
                    story.setStoryDate("今日热闻");
                    newStories.add(story);
                }
            } else {
                for (News.Story story : news.getStories()) {
                    story.setStoryDate(DateUtil.getStringDate(DateUtil.FORMAT_MM_DD, dateString) + " " + DateUtil.getDateWeek(dateString));
                    newStories.add(story);
                }
            }
            news.setStories(newStories);
            return news;
        }
    };

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
