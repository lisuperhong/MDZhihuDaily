package com.lzh.mdzhihudaily_mvp.presenter;

import android.support.annotation.NonNull;

import com.lzh.mdzhihudaily_mvp.contract.ThemeDailyContract;
import com.lzh.mdzhihudaily_mvp.model.DataRepository;
import com.lzh.mdzhihudaily_mvp.model.Entity.ThemeNews;

import rx.Subscriber;
import rx.Subscription;

/**
 * @author lzh
 * @desc:
 * @date Created on 2017/3/5 23:59
 * @github: https://github.com/lisuperhong
 */

public class ThemeDailyPresenter implements ThemeDailyContract.Presenter {

    private ThemeDailyContract.View themeDailyView;
    private int themeId;
    private Subscription subscription;

    public ThemeDailyPresenter(@NonNull ThemeDailyContract.View view, int themeId) {
        themeDailyView = view;
        this.themeId = themeId;
    }


    @Override
    public void start() {
        themeDailyView.showLoading();
        getThemeNews(false);
    }

    @Override
    public void refreshData() {
        getThemeNews(true);
    }

    private void getThemeNews(final boolean isRefresh) {
        unsubscript();
        subscription = DataRepository.getInstance()
                .getThemeNews(themeId)
                .subscribe(new Subscriber<ThemeNews>() {
                    @Override
                    public void onCompleted() {
                        if (isRefresh) {
                            themeDailyView.stopRefreshLayout();
                        } else {
                            themeDailyView.hideLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isRefresh) {
                            themeDailyView.stopRefreshLayout();
                        } else {
                            themeDailyView.hideLoading();
                        }
                    }

                    @Override
                    public void onNext(ThemeNews themeNews) {
                        themeDailyView.setData(themeNews);
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
