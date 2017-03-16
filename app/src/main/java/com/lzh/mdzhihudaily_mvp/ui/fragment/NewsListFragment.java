package com.lzh.mdzhihudaily_mvp.ui.fragment;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.lzh.mdzhihudaily_mvp.R;
import com.lzh.mdzhihudaily_mvp.base.BaseFragment;
import com.lzh.mdzhihudaily_mvp.base.RecyclerviewItemListener;
import com.lzh.mdzhihudaily_mvp.contract.NewsListContract;
import com.lzh.mdzhihudaily_mvp.model.Entity.News;
import com.lzh.mdzhihudaily_mvp.presenter.NewsListPresenter;
import com.lzh.mdzhihudaily_mvp.ui.activity.MainActivity;
import com.lzh.mdzhihudaily_mvp.ui.activity.NewsDetailActivity;
import com.lzh.mdzhihudaily_mvp.ui.adapter.NewsListAdapter;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/3/5 14:39
 * @github: https://github.com/lisuperhong
 */

public class NewsListFragment extends BaseFragment implements NewsListContract.View, SwipeRefreshLayout.OnRefreshListener,
        RecyclerviewItemListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private NewsListAdapter adapter;
    private boolean isLoadMore = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    @Override
    protected void initView() {
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        refreshLayout.setOnRefreshListener(this);

        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return isLoadMore;
            }
        });
        recyclerview.addOnScrollListener(new ListScrollListener());
    }

    @Override
    protected void initData() {
        adapter = new NewsListAdapter(getActivity(), new ArrayList<News.Story>(), new ArrayList<News.TopStory>());
        adapter.setItemClickListener(this);
        recyclerview.setAdapter(adapter);
        presenter = new NewsListPresenter(this);
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
    public void stopLoadMore() {
        isLoadMore = false;
    }

    @Override
    public void setData(News news) {
        adapter.setRefreshDate(news.getStories(), news.getTopStories());
    }

    @Override
    public void setBeforeData(News news) {
        adapter.setBeforeNews(news.getStories());
    }

    @Override
    public void onRefresh() {
        ((NewsListPresenter) presenter).refreshData();
    }

    @Override
    public void onLoadMore() {
        ((NewsListPresenter) presenter).loadMoreData();
    }

    @Override
    public void onItemClick(long storyId) {
        NewsDetailActivity.startActivity(getActivity(), storyId);
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
            } else if (!toolbarTitle.equals(adapter.getStories().get(firstVisibilityItem - 1).getStoryDate())) {
                toolbarTitle = adapter.getStories().get(firstVisibilityItem - 1).getStoryDate();
                ((MainActivity) getActivity()).getToolbar().setTitle(toolbarTitle);
            }

            if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibilityItem == (totalItemCount - 1)) {
                isLoadMore = true;
                onLoadMore();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    }
}
