package com.lzh.mdzhihudaily_mvp.presenter;

import android.support.annotation.NonNull;

import com.lzh.mdzhihudaily_mvp.contract.NewsListContract;
import com.lzh.mdzhihudaily_mvp.model.DataRepository;
import com.lzh.mdzhihudaily_mvp.model.Entity.News;
import com.lzh.mdzhihudaily_mvp.utils.DateUtil;

import rx.Subscriber;
import rx.Subscription;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/3/5 17:09
 * @github: https://github.com/lisuperhong
 */

public class NewsListPresenter implements NewsListContract.Presenter {

    private String currentDate;
    private String beforeDate;
    private NewsListContract.View newsListView;
    private Subscription subscription;

    public NewsListPresenter(@NonNull NewsListContract.View view) {
        newsListView = view;
    }

    @Override
    public void start() {
        newsListView.showLoading();
        currentDate = DateUtil.dateToString(DateUtil.FORMAT_YMD, DateUtil.getCurrentDate());
        beforeDate = currentDate;

        getLatestNews(false);
    }

    @Override
    public void refreshData() {
        getLatestNews(true);
    }

    @Override
    public void loadMoreData() {
        unsubscript();
        subscription = DataRepository.getInstance()
                .getBeforeNews(beforeDate)
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {
                        newsListView.stopLoadMore();
                    }

                    @Override
                    public void onError(Throwable e) {
                        newsListView.stopLoadMore();
                    }

                    @Override
                    public void onNext(News news) {
                        beforeDate = news.getDate();
                        newsListView.setBeforeData(news);
                    }
                });
    }

    private void getLatestNews(final boolean isRefresh) {
        unsubscript();
        subscription = DataRepository.getInstance()
                .getLatestNews(currentDate)
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {
                        if (isRefresh) {
                            newsListView.stopRefreshLayout();
                            beforeDate = currentDate;
                        } else {
                            newsListView.hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isRefresh) {
                            newsListView.stopRefreshLayout();
                        } else {
                            newsListView.hideLoading();
                        }
                    }

                    @Override
                    public void onNext(News news) {
                        newsListView.setData(news);
                    }
                });
    }

    @Override
    public void unsubscript() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
